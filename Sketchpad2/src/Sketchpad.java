/*
 *------------------ 基本要求----------------
 * 新建空白图形文档		√
 * 打开图形文件			√
 * 保存图形文件			。
 * 直线				√
 * 矩形				√
 * 圆					。
 * 椭圆				。
 * 任意线				。
 * 添加文字				。
 * 设定颜色				√
 * 文字风格				。
 * 鼠标拖动画图添加文字（没搞懂）
 * ------------------ 选作要求----------------
 * 设定画笔				。
 * 图像添加				。
 * 图像修改橡皮			√
 * 图形填充				。
 * 线形设置				√
 * 操作撤销				√
 * 菜单				√
 * ------------------ 其他要求----------------
 * 快捷键				。
 * 
 * */


import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


/**
 * @ClassName: MyFileFilter
 * @Description: 文件过滤器类。过滤掉 非(jpg|png|bmp)的文件
 * @author: Cai Zhenhui
 * @date: Jul 20, 2019 3:12:58 AM
 *
 */
class MyFileFilter extends FileFilter{
	
	public String [] pictureTypeSet = {".jpg",".png",".bmp"};
	@Override
	public boolean accept(File f) {
		if(f != null) {
			if(f.isDirectory()) {
				return true;
			}
			//取传入文件的扩展名
			String temp =f.getName().substring(f.getName().length()-4).toLowerCase();
			for(int i=0;i<3;++i) {
				if(temp.equals(pictureTypeSet[i])) {
					return true;
				}
			}
		}
		return false;
	}
	/**
	 *在文件选择对话框中 表现为过滤的类型的描述
	 */
	@Override
	public String getDescription() {
		return "JPG or BMP or PNG";
	}
}




class DrawCanvas extends Canvas{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5719663226562173145L;
	
	
	private static final int MAX_HISTORY_RECORD=10;//最大历史记录 可撤销步骤
	private BufferedImage	image		= null;//定义Image对象的引用
	private Graphics2D 		g2d			= null;
	private int				snapShotId	=-1;//历史纪录指针
	private int				snapShotIdT	=-1;//向前撤销指针
	
	public Pen pen = null;
	public static boolean isSaved = true;
	public static int CANVAS_WIDTH = 800;
	public static int CANVAS_HEIGHT = 600; 
	/**
	 * @ClassName: Pen
	 * @Description: 画笔
	 * @author: Cai Zhenhui
	 * @date: Jul 21, 2019 2:15:04 AM
	 *
	 */
	class Pen{
		private Color		color		=null;
		private PenTypeEnum	type		=PenTypeEnum.Line;
		private int			lineType	=0;
		private int			size		=1;
		
		
		/**
		 * @param color
		 * @param type
		 * @param lineType
		 * @param size
		 */
		public Pen(Color color, PenTypeEnum type, int lineType, int size) {
			this.color = color;
			this.type = type;
			this.lineType = lineType;
			this.size = size;
		}

		/**
		 * 
		 */
		public Pen() {
			this.color = new Color(0,0,0);
			this.type = PenTypeEnum.Line;
			this.lineType = 0;
			this.size = 1;
		}

		/**
		 * @return the color
		 */
		public Color getColor() {
			return color;
		}

		/**
		 * @param color the color to set
		 */
		public void setColor(Color color) {
			this.color = color;
		}

		/**
		 * @return the type
		 */
		public PenTypeEnum getType() {
			return type;
		}

		/**
		 * @param type the type to set
		 */
		public void setType(PenTypeEnum type) {
			this.type = type;
		}

		/**
		 * @return the lineType
		 */
		public int getLineType() {
			return lineType;
		}

		/**
		 * @param lineType the lineType to set
		 */
		public void setLineType(int lineType) {
			this.lineType = lineType;
		}

		/**
		 * @return the size
		 */
		public int getSize() {
			return size;
		}

		/**
		 * @param size the size to set
		 */
		public void setSize(int size) {
			this.size = size;
		}
	}
	
	
	public DrawCanvas(BufferedImage image) {
		super();
		this.setImage(image);
		initialize();
	}
	private void initialize() {
		pen = new Pen(new Color(123,0,123),PenTypeEnum.Line,1,3);//测试用
		//pen = new Pen();//非调试版本 因该调用无参数构造函数
		this.setVisible(true);
		saveStep();
	}

	/**
	 * @Description: 获取BufferedImage对象
	 * @return the current BufferedImage
	 */
	public BufferedImage getImage() {
		return image;
	}
	/**
	 * @Description: 设置BufferedImage对象
	 * @param image
	 */
	public void setImage(BufferedImage image) {
		this.image=image;
		this.g2d = (Graphics2D)image.getGraphics();//获取绘图对象
	}
	
	/**
	 * @return the pen
	 */
	public Pen getPen() {
		return pen;
	}
	/**
	 * @param pen the pen to set
	 */
	public void setPen(Pen pen) {
		this.pen = pen;
	}
	
	/**
	 * @Description: 获取Graphics2D对象
	 * @return the current Graphics2D
	 */
	/*public Graphics2D getGraphics2D() {
		return (Graphics2D)image.getGraphics();
	}*/
	
	/**
	 * @Description: 设置Graphics2D对象
	 * @param g2d
	 */
	/*public void setGraphics2D(Graphics2D g2d) {
		this.g2d=g2d;
	}*/
	
	
	
	
	/**
	 * @Description: 绘图函数，调用前请通过DrawCanvas.pen.setType()设置画笔类型
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 */
	public void draw(int x1,int y1,int x2,int y2) {
		
		g2d.setColor(pen.color);
		switch (pen.type) {
		case Arbitrary_line:{
			this.drawArbitraryLine(x1, y1, x2, y2);
			break;
		}
		case Cricle:{
			this.drawCricle(x1, y1, x2, y2);
			break;
		}
		case Ellipse:{
			this.drawEllipse(x1, y1, x2, y2);
			break;
		}
		case Eraser:{
			this.drawEraser(x1, y1, x2, y2);
			break;
		}
		case Line:{
			this.drawLine(x1, y1, x2, y2);
			break;
		}
		case Pointer:{
			this.drawPointer(x1, y1, x2, y2);
			break;
		}
		case Rectangle:{
			this.drawRectangle(x1, y1, x2, y2);
		}
		case String:{
			this.drawString(x1, y1, x2, y2);
			break;
		}
		default:
			break;
		
		}//end switch
		saveStep();
		this.repaint();//重绘 ，执行它之后新画上去的东西才会显示
	}
	
	public void drawImage(BufferedImage newImage) {
		this.image = new BufferedImage(DrawCanvas.WIDTH, DrawCanvas.HEIGHT,newImage.getType());
		this.g2d = (Graphics2D)this.image.getGraphics();
		//this.setImage((BufferedImage)image);
		//g2d.drawImage(image,0,0,this.getWidth(),this.getHeight(),null);
		g2d.drawImage(newImage,0,0,DrawCanvas.WIDTH, DrawCanvas.HEIGHT,null);
		this.repaint();
	}
	
	
	private void drawLine(int x1,int y1,int x2,int y2) {
		//由于有pen.size的存在,所以要设置画笔
		float width=1.0f;
		int cap;
		int join;
		float miterLimit=10.0f;
		float[] dash=null;
		float dash_phase=0f;
		BasicStroke basicStroke =null;
		switch(this.pen.lineType) {
		case 0:{//实线
			width=(float)pen.size;
			basicStroke = new BasicStroke(width);
			break;
		}
		case 1:{//虚线5:2
			width=(float)pen.size;
			cap=BasicStroke.CAP_BUTT;
			join=BasicStroke.JOIN_BEVEL;
			dash =new float[2];
			dash[0]=5.0f;dash[1]=2.0f;
			basicStroke = new BasicStroke(width,cap,join,miterLimit,dash,dash_phase);
			break;
		}
		case 2:{//点线
			width=(float)pen.size;
			cap=BasicStroke.CAP_BUTT;
			join=BasicStroke.JOIN_BEVEL;
			dash =new float[2];
			dash[0]=width;dash[1]=width;
			basicStroke = new BasicStroke(width,cap,join,miterLimit,dash,dash_phase);
			break;
		}
		}//end switch
		g2d.setStroke(basicStroke);
		g2d.drawLine(x1, y1, x2, y2);
		
	}
	private void drawRectangle(int x1,int y1,int x2,int y2) {
		/* 由传入的x1, y1, x2, y2构造新的坐标
		 *  p1--------- 	p1--------p2
		 *   |        | 	 |        |
		 *   |        | 	 |        |
		 *   ---------p2	p4--------p3
		 * */
		int 	x3=x2,y3=y2,
				x4=x1,y4=y2;
		x2=x3;
		y2=y1;
		this.drawLine(x1, y1, x2, y2);
		this.drawLine(x2, y2, x3, y3);
		this.drawLine(x3, y3, x4, y4);
		this.drawLine(x4, y4, x1, y1);
	}
	private void drawArbitraryLine(int x1,int y1,int x2,int y2) {//等待完成
		;
	}
	private void drawEraser(int x1,int y1,int x2,int y2) {
		g2d.setColor(new Color(255,255,255));
		BasicStroke basicStroke = new BasicStroke(2.5f*(float)pen.size);//橡皮比普通笔宽一点，好擦。
		g2d.setStroke(basicStroke);
		g2d.drawLine(x1, y1, x2, y2);
	}
	private void drawEllipse(int x1,int y1,int x2,int y2) {//等待完成
		;
	}
	private void drawCricle(int x1,int y1,int x2,int y2) {//等待完成
		;
	}
	private void drawString(int x1,int y1,int x2,int y2) {//等待完成
		;
	}
	private void drawPointer(int x1,int y1,int x2,int y2) {//等待完成
		;
	}
	
	private void saveStep() {
		snapShotId++;
		snapShotIdT=snapShotId;//调用此方法时，若（snapShotIdT<snapShotId）即后续历史纪录废除。两个指针同步.
		//也表示直到下次撤销前不再能重做
		System.out.println("svaeStep()"+ snapShotId+" " +snapShotIdT);
		try {
			ImageIO.write(image,"bmp",new File("bin/temp"+snapShotId%MAX_HISTORY_RECORD+".bmp"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void clearImage() {
		g2d.setColor(new Color(255,0,255));
		g2d.fillRect(0, 0, DrawCanvas.CANVAS_WIDTH, DrawCanvas.CANVAS_HEIGHT);
		this.repaint();
	}
	public void savePicture() {
		try {
			ImageIO.write(image,"bmp",new File("bin/temp"+snapShotId%MAX_HISTORY_RECORD+".bmp"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		JFileChooser fileChooser = new JFileChooser();//创建一个文件选择对话框
		fileChooser.setApproveButtonText("Save");
		fileChooser.changeToParentDirectory();//切换到当前目录
		//fileChooser.setFileFilter(new MyFileFilter());//添加文件类型过滤器
		int returntValue = fileChooser.showSaveDialog(null);//显示保存对话框
		
		if(returntValue==JFileChooser.APPROVE_OPTION) {//判断用户是否选择了文件
			try {
				//System.out.println(fileChooser.getFileSelectionMode());
				
				File file =fileChooser.getSelectedFile();//获得文件对象
				String name=fileChooser.getName(file);
				//获取扩展名
				String extendName =name.substring(name.length()-4).toLowerCase();
				//System.out.println(fileChooser.getSelectedFile());
				ImageIO.write(image,extendName,file);
				DrawCanvas.isSaved=true;
				//File file =fileChooser.getSelectedFile();//获得文件对象
				//canvas.drawImage(ImageIO.read(file));
				//image=canvas.getImage();//更新image
				//canvas.repaint();
			}catch(Exception e1) {
				System.out.println("Error!");
				System.out.println(e1);
			}
		}
	}
	/**
	 * @Description: 撤销操作。
	 * @throws IOException
	 */
	public void unDO() throws IOException {
		if(snapShotId<=0)
		{
			return;//没有历史记录可供撤销
		}
		if(snapShotIdT<=0) {
			return;//已撤销到最初始状态 无法在撤销
		}
		System.out.println("Undo调用");
		snapShotIdT-=1;
		System.out.println("undo()"+ snapShotId+" " +snapShotIdT);
		//不同操作系统下的目录分隔符不一样，为了跨平台应该用File.separator 
		image=ImageIO.read(new File("bin"+File.separator+"temp"+snapShotIdT%MAX_HISTORY_RECORD+".bmp"));
		
		//System.out.println(this.getWidth()+","+this.getHeight());
		//g2d.drawImage(image,0,0,this.getWidth(),this.getHeight(),null);
		g2d.drawImage(image,0,0,DrawCanvas.WIDTH,DrawCanvas.HEIGHT,null);
		this.repaint();
		g2d =(Graphics2D)image.getGraphics();
		//isReDO=true;//表示可以重做
	}
	public void reDO() throws IOException {
		if(snapShotIdT<snapShotId) {//表示可以重做
			System.out.println("Redo调用");
			snapShotIdT+=1;
			System.out.println("redo()"+ snapShotId+" " +snapShotIdT);
			
			//不同操作系统下的目录分隔符不一样，为了跨平台应该用File.separator 
			image=ImageIO.read(new File("bin"+File.separator+"temp"+snapShotIdT%MAX_HISTORY_RECORD+".bmp"));
			//System.out.println(this.getWidth()+","+this.getHeight());
			//g2d.drawImage(image,0,0,this.getWidth(),this.getHeight(),null);
			g2d.drawImage(image,0,0,DrawCanvas.WIDTH,DrawCanvas.HEIGHT,null);
			this.repaint();
			g2d =(Graphics2D)image.getGraphics();
		}
	}
	
	/**
	 * 重写Canvas.paint()方法，在画布上绘制图形
	 */
	@Override
	public void paint(Graphics g) {
		System.out.println("paint调用");
		
		g.drawImage(image, 0, 0, null);//在画布上绘图
	}
	
	/**
	 * 重写Canvas.update()方法，解决闪烁问题
	 */
	@Override
	public void update(Graphics g) {
		System.out.println("update调用");
		this.paint(g);
	}
	
}

/**
 * @ClassName: IconTextCellRenderImpl
 * @Description: 左侧工具条渲染器接口的实现类
 * @author: Cai Zhenhui
 * @date: Jul 20, 2019 12:06:45 AM
 *
 */
class IconTextCellRenderImpl extends JPanel implements ListCellRenderer<Object>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8677561145772123234L;
	private ImageIcon icon;	//声明列表项的图标
	private String text;	//声明列表项的文本
	private Color backColor;
	private Color foreColor;

	//实现接口(ListCellRenderer)中的方法
	@Override
	public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
			boolean cellHasFocus) {
		//不同操作系统下的目录分隔符不一样，为了跨平台应该用File.separator 
		icon = new ImageIcon("src"+File.separator+"icon"+File.separator+value+".png");//获取列表项图标
		text = value.toString();//获取列表项的文本
		//获取前景色和背景色
		if(isSelected) {
			backColor = list.getSelectionBackground();
			foreColor = list.getSelectionForeground();
		}
		else {
			backColor = list.getBackground();
			foreColor = list.getForeground();
		}
		return this;
	}
	
	//绘制列表框
	public void paintComponent(Graphics g) {
		Image img = icon.getImage();//获取图标
		int iconWidth	= img.getWidth(null);//获取图标的宽度
		int iconHeight	= img.getHeight(null);//获取图标的高度
		g.setColor(backColor);
		g.fillRect(0, 0, getWidth(), getHeight());//使用背景色绘制整个列表框
		
		g.setColor(foreColor);
		g.drawImage(img,0,(getHeight()-iconHeight)/2,null);//绘制图标
		
		g.setFont(new Font("Default",Font.BOLD,14));
		g.drawString(text, iconWidth+5,getHeight()/2+4 );//绘制文本
	}
	
	public Dimension getPreferredSize() {
		return new Dimension(120,35);//列表项首选大小
	}
}



public class Sketchpad extends JFrame implements MouseListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4670806987716816519L;

	private JPanel 			contentPane;
	
	//文件相关
	public File				file=null;
	//public boolean			isSaved=true;//表示当前图片是否被保存了
	
	//ToolsList相关
	public int 				lastSelectIndex=0;//列表框现行选中项
	
	//canvas相关
	public BufferedImage 	image;
	//public Graphics2D 		g2d;//获取绘图对象
	public DrawCanvas 		canvas ;//创建画布对象
	public Color 			foreColor ;
	public Color			penColor;
	public boolean 			isPressed = false;
	public int				x1=0,y1=0;
	public int				x2=0,y2=0;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {//程序入口
		/*
		Sketchpad frame = new Sketchpad();
		frame.setVisible(true);*/
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Sketchpad frame = new Sketchpad();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Sketchpad() {
		setTitle("Sketchpad");
		addWindowListener(new WindowAdapter() {
			/**
			 *用户单击右上角关闭按钮时，产生该事件.
			 */
			@Override
			public void windowClosing(WindowEvent e) {
				if(!trySave()) {//提醒用户是否需要保存
					return ;
				}
				System.exit(0);//执行关闭操作
			}
		});
		//设置点击右上角关闭按钮时不进行关闭操作，而是产生一个windowevent事件，
		//需要窗口注册winodwListener监听器，并在windowClosing方法中处理
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 961, 683);
		
		//----------------------菜单--------------------
		JMenuBar menuBar = new JMenuBar();//创建一个菜单栏
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");//创建菜单 File
		menuBar.add(mnFile);
		
		JMenuItem mntmNew = new JMenuItem("New");//创建菜单项
		mntmNew.addActionListener(new ActionListener() {//为 New 菜单项添加事件
			public void actionPerformed(ActionEvent e) {
				if(!trySave()) {//提醒用户是否需要保存
					return ;
				}
				canvas.clearImage();
				DrawCanvas.isSaved=true;//新建图像之后需要将isSave置为true
			}
		});
		mnFile.add(mntmNew);//将菜单项加入菜单
		
		JMenuItem mntmOpen = new JMenuItem("Open");
		mntmOpen.addActionListener(new ActionListener() {//为 Open 菜单项添加事件
			public void actionPerformed(ActionEvent e) {
				if(!trySave()) {//提醒用户是否需要保存
					return ;
				}
				JFileChooser fileChooser = new JFileChooser();//创建文件选择对话框
				fileChooser.setApproveButtonText("Open");
				fileChooser.setFileFilter(new MyFileFilter());//添加文件类型过滤器
				int returntValue = fileChooser.showOpenDialog(getContentPane());//打开文件选择对话框
				if(returntValue==JFileChooser.APPROVE_OPTION) {//判断用户是否选择了文件
					try {
						file =fileChooser.getSelectedFile();//获得文件对象
						canvas.clearImage();
						//image = canvas.getImage();
						BufferedImage imageTemp = ImageIO.read(file);
						System.out.println(imageTemp.getType());
						//Graphics2D g2d = (Graphics2D)image.getGraphics();
						//g2d.drawImage(imageTemp, 0, 0, DrawCanvas.WIDTH, DrawCanvas.HEIGHT, null);
						//canvas.
						canvas.drawImage(imageTemp);
						
						
						/*image =ImageIO.read(file);//将选的文件读到image对象
						
						System.out.println(image.getWidth());
						System.out.println(image.getHeight());
						
						//将打开的新图片缩放
						canvas.clearImage();
						canvas.setImage(image);
						canvas.repaint();*/
						//image.getGraphics().drawImage(imageTemp, 0, 0, DrawCanvas.WIDTH, DrawCanvas.HEIGHT, null);
						//canvas.setImage(image);
						//canvas.repaint();
						//canvas.drawImage(ImageIO.read(file));
						//image=canvas.getImage();//更新image
						//canvas.repaint();
					}catch(Exception e1) {
						System.out.println("Error!");
						System.out.println(e1);
					}
				}
				DrawCanvas.isSaved=true;//新建图像之后需要将isSave置为true
			}
		});
		mnFile.add(mntmOpen);
		
		JMenuItem mntmSave = new JMenuItem("Save");
		mnFile.add(mntmSave);
		
		JMenuItem mntmSaveAs = new JMenuItem("Save As");
		mnFile.add(mntmSaveAs);
		
		JMenu mnEdit = new JMenu("Edit");
		menuBar.add(mnEdit);
		
		JMenuItem mntmUndo = new JMenuItem("Undo");
		mntmUndo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {//为 Undo 菜单添加事件
				System.out.println("Undo Start");
				try {
					canvas.unDO();//撤销
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				System.out.println("Undo End");
			}
		});
		mnEdit.add(mntmUndo);
		
		JMenuItem mntmRedo = new JMenuItem("Redo");
		mntmRedo.addActionListener(new ActionListener() {//为 Redo菜单添加事件
			public void actionPerformed(ActionEvent e) {
				System.out.println("redo Start");
				try {
					canvas.reDO();//重做
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				System.out.println("redo End");
			}
		});
		mnEdit.add(mntmRedo);
		
		JMenu mnShow = new JMenu("Show");
		menuBar.add(mnShow);
		
		JMenuItem mntmColor = new JMenuItem("Color");
		mntmColor.addActionListener(new ActionListener() {//为 Color 菜单添加事件
			public void actionPerformed(ActionEvent e) {
				//打开颜色选择对话框
				Color color = JColorChooser.showDialog(contentPane, //将新对话框作为contentPane容器的一部分
						"Edit Color",//对话框标题
						canvas.pen.getColor()//初始颜色
						);
				canvas.pen.setColor(color);//设置新颜色
			}
		});
		mnShow.add(mntmColor);
		
		JMenuItem mntmLineType = new JMenuItem("Line Type");
		mnShow.add(mntmLineType);
		
		JMenuItem mntmLineSize = new JMenuItem("Line Size");
		mnShow.add(mntmLineSize);
		
		JMenuItem mntmFont = new JMenuItem("Font");
		mnShow.add(mntmFont);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		//-------------------左侧工具栏------------------
		JList<Object> list = new JList<Object>();
		list.addListSelectionListener(new ListSelectionListener() {//为左侧工具栏添加事件
			public void valueChanged(ListSelectionEvent e) {
				lastSelectIndex=e.getLastIndex();//获取用户点击的选中项
			}
		});
		IconTextCellRenderImpl cr = new IconTextCellRenderImpl();//实例化渲染器
		//String [] listItem = {"指针","直线","任意线","橡皮","矩形","圆","椭圆","文字"};//列表框项目文本数组
		String [] listItem = PenTypeEnum.getPenTypeList();//从枚举类里获得工具条项目的文本数组
		list.setCellRenderer(cr);//渲染
		list.setModel(new DefaultComboBoxModel<Object>(listItem));
		contentPane.add(list, BorderLayout.WEST);
		
		//--------------------Canvas-------------------
		//创建一个图像对象，以后就在这上边画了
		//image = new BufferedImage(this.getWidth()-list.getWidth(),this.getHeight()-menuBar.getHeight(),BufferedImage.TYPE_INT_BGR);
		image = new BufferedImage(DrawCanvas.CANVAS_WIDTH, DrawCanvas.CANVAS_HEIGHT,
				BufferedImage.TYPE_INT_BGR);
		
		Graphics2D g2d = //仅允许在初始时使用一次---------
				(Graphics2D) image.getGraphics();//获取绘图对象
		g2d.setColor(new Color(255,5,255));//初始填充为白色
		g2d.fillRect(0, 0, DrawCanvas.CANVAS_WIDTH, DrawCanvas.CANVAS_HEIGHT);
		g2d=null;//---------------------------------
		
		canvas = new DrawCanvas(image);//创建画布对象
		canvas.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				if(isPressed)
				{
					System.out.println(e);
				}
				
			}
		});
		//canvas.setImage(image);//设置图像对象
		canvas.addMouseListener(this);//注册鼠标监听器
		contentPane.add(canvas,java.awt.BorderLayout.CENTER);
		
		
		
	}
	
	/**
	 * @Description: 当用户执行新建、打开新图片、关闭窗口等类似可能丢失数据的操作时。
	 * 应该调用此方法，尝试提醒用户保存图片。
	 * 
	 * @return 	true:图片已保存，可以继续执行后续操作
	 * 			false:用户选择取消or 保存图片失败表示不可及继续执行后续操作
	 */
	public boolean trySave() {
		if(!DrawCanvas.isSaved)//判断当前图片是否已被改变而且没有保存 
		{
			//需要提醒用户是否保存
			//创建一个对话框
			int flag = JOptionPane.showConfirmDialog(null, //parentComponent
					"Do you want to save changes to Untitled?",//message
					"Save",//title
					JOptionPane.YES_NO_CANCEL_OPTION);//按钮选项
			switch(flag) {
			case JOptionPane.YES_OPTION:{//用户选择保存
				//先保存然后流程继续向下
				canvas.savePicture();
				if(!DrawCanvas.isSaved)
				{
					return false;//没有保存 返回
				}
				break;
			}
			case JOptionPane.NO_OPTION:{//用户选择不保存
				break;//流程继续向下
			}
			case JOptionPane.CANCEL_OPTION:{//用户选择取消
				return false;
			}
			}
		}//流程从上边流出时，表示用户已经做好选择（已保存or保存）
		return true;
	}
	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println(e);
		//canvas.clearImage();
		//PenTypeEnum test= PenTypeEnum.Pointer;
		for(String str:PenTypeEnum.getPenTypeList()) {
			System.out.println(str);
		}
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getSource()==canvas) {
			x1=e.getX();
			y1=e.getY();
			isPressed=true;
		}
		
	}

	/**
	 *可能更改图片,所以变量isSaved赋值为false
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getSource()==canvas) {
			if(isPressed) {
				x2=e.getX();
				y2=e.getY();
				System.out.println(PenTypeEnum.values()[this.lastSelectIndex]);
				canvas.pen.setType(PenTypeEnum.values()[this.lastSelectIndex]);
				canvas.draw(x1, y1, x2, y2);
				DrawCanvas.isSaved=false;
				isPressed=false;
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
