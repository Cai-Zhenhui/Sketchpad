/*
 *------------------ 基本要求----------------
 * 新建空白图形文档			√
 * 打开图形文件			√
 * 保存图形文件			√
 * 直线					√
 * 矩形					√
 * 圆					√
 * 椭圆					√
 * 任意线					√
 * 添加文字				√
 * 设定颜色				√
 * 文字风格				√
 * 鼠标拖动画图添加文字（没搞懂）
 * ------------------ 选作要求----------------
 * 设定画笔				√
 * 图像添加				？
 * 图像修改橡皮			√
 * 线形设置				√
 * 操作撤销				√
 * 菜单					√
 * ------------------ 其他要求----------------
 * 快捷键					。
 * 
 * */


import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
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
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JCheckBoxMenuItem;
import java.awt.event.MouseAdapter;


/**
 * @ClassName: MyFileFilter
 * @Description: 文件过滤器类。过滤掉 非(jpg|png|bmp)的文件
 * @author: Cai Zhenhui
 * @date: Jul 20, 2019 3:12:58 AM
 *
 */
class MyFileFilter extends FileFilter{
	public String [] pictureTypeSet = {"JPG","PNG","BMP"};
	@Override
	public boolean accept(File f) {
		if(f != null) {
			if(f.isDirectory()) {
				return true;
			}
			//取传入文件的扩展名
			String fileName=f.getName();
			String extendName=null;
			int pointIndex=fileName.lastIndexOf('.');
			if(pointIndex!=-1) {
				extendName=fileName.substring(pointIndex+1).toUpperCase();
				//System.out.println(extendName);//eg. ".doc"
			}
			else {
				//No extension
				return false;
			}
			for(int i=0;i<3;++i) {
				if(extendName.equals(pictureTypeSet[i])) {
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

/**
 * @ClassName: MyFileFilter
 * @Description: 文件过滤器类。过滤掉 非(jpg)的文件
 * @author: Cai Zhenhui
 * @date: Jul 20, 2019 3:12:58 AM
 *
 */
class MyFileFilterJPG extends FileFilter{
	public String pictureType = "JPG";
	@Override
	public boolean accept(File f) {
		if(f != null) {
			if(f.isDirectory()) {
				return true;
			}
			//取传入文件的扩展名
			String fileName=f.getName();
			String extendName=null;
			int pointIndex=fileName.lastIndexOf('.');
			if(pointIndex!=-1) {
				extendName=fileName.substring( pointIndex+1).toUpperCase();
			}
			else {
				//No extension
				return false;
			}
			//判断获取到的扩展名是否是此过滤器所支持的
			if(extendName.equals(pictureType)) {
				return true;
			}
		}
		return false;
	}
	/**
	 *在文件选择对话框中 表现为过滤的类型的描述
	 */
	@Override
	public String getDescription() {
		return pictureType;
	}
}

/**
 * @ClassName: MyFileFilter
 * @Description: 文件过滤器类。过滤掉 非(jpg)的文件
 * @author: Cai Zhenhui
 * @date: Jul 20, 2019 3:12:58 AM
 *
 */
class MyFileFilterPNG extends FileFilter{
	public String pictureType = "PNG";
	@Override
	public boolean accept(File f) {
		if(f != null) {
			if(f.isDirectory()) {
				return true;
			}
			//取传入文件的扩展名
			String fileName=f.getName();
			String extendName=null;
			int pointIndex=fileName.lastIndexOf('.');
			if(pointIndex!=-1) {
				extendName=fileName.substring( pointIndex+1).toUpperCase();
			}
			else {
				//No extension
				return false;
			}
			//判断获取到的扩展名是否是此过滤器所支持的
			if(extendName.equals(pictureType)) {
				return true;
			}
		}
		return false;
	}
	/**
	 *在文件选择对话框中 表现为过滤的类型的描述
	 */
	@Override
	public String getDescription() {
		return pictureType;
	}
}

/**
 * @ClassName: MyFileFilter
 * @Description: 文件过滤器类。过滤掉 非(jpg)的文件
 * @author: Cai Zhenhui
 * @date: Jul 20, 2019 3:12:58 AM
 *
 */
class MyFileFilterBMP extends FileFilter{
	public String pictureType = "BMP";
	@Override
	public boolean accept(File f) {
		if(f != null) {
			if(f.isDirectory()) {
				return true;
			}
			//取传入文件的扩展名
			String fileName=f.getName();
			String extendName=null;
			int pointIndex=fileName.lastIndexOf('.');
			if(pointIndex!=-1) {
				extendName=fileName.substring( pointIndex+1).toUpperCase();
			}
			else {
				//No extension
				return false;
			}
			//判断获取到的扩展名是否是此过滤器所支持的
			if(extendName.equals(pictureType)) {
				return true;
			}
		}
		return false;
	}
	/**
	 *在文件选择对话框中 表现为过滤的类型的描述
	 */
	@Override
	public String getDescription() {
		return pictureType;
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
		private Color			color		=null;
		private PenTypeEnum		type		=PenTypeEnum.Line;
		/*
		 * 0:Solid
		 * 1:Dashed
		 * 2:Dotted
		 * */
		private LineTypeEnum	lineType	=LineTypeEnum.SOLID;
		private int				size		=1;
		private Font			font		=null;
		
		/**
		 * @param color
		 * @param type
		 * @param lineType
		 * @param size
		 */
		public Pen(Color color, PenTypeEnum type, LineTypeEnum lineType, int size, Font font) {
			this.color = color;
			this.type = type;
			this.lineType = lineType;
			this.size = size;
			this.font=font;
		}

		/**
		 * 
		 */
		public Pen() {
			this.color = new Color(0,0,0);
			this.type = PenTypeEnum.Line;
			this.lineType = LineTypeEnum.SOLID;//solid
			this.size = 1;
			this.font = new Font("Default",Font.PLAIN,14);
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
		public LineTypeEnum getLineType() {
			return lineType;
		}

		/**
		 * @param lineType the lineType to set
		 */
		public void setLineType(LineTypeEnum lineType) {
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

		/**
		 * @return the font
		 */
		public Font getFont() {
			return font;
		}

		/**
		 * @param font the font to set
		 */
		public void setFont(Font font) {
			this.font = font;
		}
	}
	
	
	public DrawCanvas(BufferedImage image) {
		super();
		this.setImage(image);
		initialize();
	}
	private void initialize() {
		//pen = new Pen(new Color(123,0,123),PenTypeEnum.Line,1,3);//测试用
		pen = new Pen();//非调试版本  调用无参数构造函数
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
		
		g2d.setColor(pen.getColor());
		switch (pen.getType()) {
		/*case Arbitrary_line:{
			this.drawArbitraryLine(x1, y1, x2, y2);
			break;
		}*/
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
			break;
		}
		/*case String:{
			this.drawString(x1, y1, x2, y2);
			break;
		}*/
		default:
			break;
		
		}//end switch
		saveStep();
		this.repaint();//重绘 ，执行它之后新画上去的东西才会显示
	}
	
	private void setBasicStroke() {
		float width=1.0f;
		int cap;
		int join;
		float miterLimit=10.0f;
		float[] dash=null;
		float dash_phase=0f;
		BasicStroke basicStroke =null;
		
		width=(float)pen.size;
		switch(this.pen.lineType) {
		case SOLID :{//Solid 实线
			basicStroke = new BasicStroke(width);
			break;
		}
		case DASHED:{//Dashed 虚线
			cap=BasicStroke.CAP_BUTT;
			join=BasicStroke.JOIN_BEVEL;
			dash =new float[2];
			dash[0]=12.0f;dash[1]=9.0f;
			basicStroke = new BasicStroke(width,cap,join,miterLimit,dash,dash_phase);
			break;
		}
		case DOTTED:{//Dotted 点线
			cap=BasicStroke.CAP_BUTT;
			join=BasicStroke.JOIN_BEVEL;
			dash =new float[2];
			dash[0]=3.0f;dash[1]=3.0f;
			basicStroke = new BasicStroke(width,cap,join,miterLimit,dash,dash_phase);
			break;
		}
		}//end switch
		g2d.setStroke(basicStroke);
	}
	
	public void drawImage(BufferedImage newImage) {
		g2d.drawImage(newImage, 0, 0, DrawCanvas.CANVAS_WIDTH,DrawCanvas.CANVAS_HEIGHT,null);
		this.saveStep();
		this.repaint();
	}
	
	
	private void drawLine(int x1,int y1,int x2,int y2) {
		this.setBasicStroke();
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
		//drawLine会调用this.setBasicStroke();所以在此不必调用
		this.drawLine(x1, y1, x2, y2);
		this.drawLine(x2, y2, x3, y3);
		this.drawLine(x3, y3, x4, y4);
		this.drawLine(x4, y4, x1, y1);
	}
	public void drawArbitraryLine(int x1,int y1,int x2,int y2) {
		g2d.setColor(pen.getColor());
		this.setBasicStroke();
		g2d.drawLine(x1, y1, x2, y2);
		this.repaint();
	}
	private void drawEraser(int x1,int y1,int x2,int y2) {
		g2d.setColor(new Color(255,255,255));
		BasicStroke basicStroke = new BasicStroke(3.5f*(float)pen.size);//橡皮比普通笔宽一点，好擦。
		g2d.setStroke(basicStroke);
		g2d.drawLine(x1, y1, x2, y2);
	}
	private void drawEllipse(int x1,int y1,int x2,int y2) {
		/*
		 * p1 ↗ →→ ↘
		 *  ↗              ↘
		 * ↑        ↓
		 * ↑        ↓
		 *  ↖                ↙
		 *   ↖ ← ← ↙     p2
		 * 
		 * */
		int width=(x2-x1);
		int height=(y2-y1);
		
		this.setBasicStroke();
		g2d.drawOval(x1, y1, width, height);
	}
	private void drawCricle(int x1,int y1,int x2,int y2) {
		/*
		 * p1 ↗ →→ ↘
		 *  ↗              ↘
		 * ↑        ↓
		 * ↑        ↓
		 *  ↖                ↙
		 *   ↖ ← ← ↙     p2
		 * 
		 * */
		int diameter=x2-x1;
		if(diameter>(y2-y1)) {//使用长和宽两者中小的作为直径 以确保是圆
			diameter=y2-y1;
		}
		this.setBasicStroke();
		g2d.drawOval(x1, y1, diameter, diameter);
	}
	public void drawString(String string, int x1,int y1,int x2,int y2) {
		g2d.setColor(pen.getColor());
		g2d.setFont(pen.getFont());
		//System.out.println(pen.getFont());
		g2d.drawString(string, x1, y1);
		saveStep();
		this.repaint();
	}
	private void drawPointer(int x1,int y1,int x2,int y2) {
		;
	}
	
	public void saveStep() {
		snapShotId++;
		snapShotIdT=snapShotId;//调用此方法时，若（snapShotIdT<snapShotId）即后续历史纪录废除。两个指针同步.
		//也表示直到下次撤销前不再能重做
		//System.out.println("svaeStep()"+ snapShotId+" " +snapShotIdT);
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
		
		try {
			//get extension
			String fileName=Sketchpad.file.getName();
			String extendName=null;
			int pointIndex=fileName.lastIndexOf('.');
			if(pointIndex!=-1) {
				extendName=fileName.substring( pointIndex+1).toUpperCase();
			}
			else {
				//No extension
				return ;
			}
			
			ImageIO.write(image,extendName,Sketchpad.file);//将图片内容写入文件
			DrawCanvas.isSaved=true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void saveAsPicture() {
		//String [] typeSet = {"JPG","PNG","BMP"};
		try {
			ImageIO.write(image,"bmp",new File("bin/temp"+snapShotId%MAX_HISTORY_RECORD+".bmp"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		JFileChooser fileChooser = new JFileChooser();//创建一个文件选择对话框
		fileChooser.setApproveButtonText("Save");
		fileChooser.changeToParentDirectory();//切换到当前目录
		fileChooser.removeChoosableFileFilter(fileChooser.getFileFilter());//删除默认过滤器（*.*）
		//添加自己的过滤器
		fileChooser.setFileFilter(new MyFileFilterJPG());
		fileChooser.setFileFilter(new MyFileFilterPNG());
		fileChooser.setFileFilter(new MyFileFilterBMP());
		int returntValue = fileChooser.showSaveDialog(null);//显示保存对话框
		
		if(returntValue==JFileChooser.APPROVE_OPTION) {//判断用户是否选择了文件
			try {
				System.out.println("Client choose file");
				//System.out.println(fileChooser.getFileSelectionMode());
				
				File file =fileChooser.getSelectedFile();//获得文件对象
				
				String name=fileChooser.getName(file);
				//get file name do not include extension
				int pointIndex=name.lastIndexOf('.');
				if(pointIndex!=-1) {
					//find point
					name=name.substring(0, pointIndex);
				}
				else {
					//not point = The user did not enter an extension.
				}
				String extendName = fileChooser.getFileFilter().getDescription();//Get picture type from the file filter 
				
				File newFile = new File(file.getParent()+name+"."+extendName);
				
				//Determine if the file selected by the user exists
				if(file.exists()||newFile.exists()) {
					//exists
					System.out.println("exists");
					
					//用户选择的文件已存在，创建一个对话框让用户选择是否覆盖掉
					int flag = JOptionPane.showConfirmDialog(null, //parentComponent
							newFile.getName()+" already exists."+System.getProperty("line.separator")+//跨平台换行符
							"Do you want to replace to it?",//message
							"Confirm Save As",//title
							JOptionPane.YES_NO_OPTION);//按钮选项
					switch(flag) {
					case JOptionPane.YES_OPTION:{//用户选择覆盖
						newFile.delete();
						break;//流出即可
					}
					case JOptionPane.NO_OPTION:{//用户选择不覆盖，重新输入文件名
						this.saveAsPicture();
						return ;//返回
					}
					}
				}
				else {
					System.out.println("not exists");
					//not exists
					if(file.createNewFile()) {
						;//success
					}
					else {
						System.out.println("Error! file.createNewFile() faild");
						return ;
					}
				}
				
				if(file.renameTo(newFile)) {
					System.out.println("Rename successful!");
				}
				else {
					System.out.println("Rename faild!");
				}
				//System.out.println(file.getName());//此时还是重命名之前的 file
				ImageIO.write(image,extendName,newFile);//将图片内容写入文件
				Sketchpad.file=newFile;
				DrawCanvas.isSaved=true;
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
		//System.out.println("paint调用");
		g.drawImage(image, 0, 0, null);//在画布上绘图
	}
	
	/**
	 * 重写Canvas.update()方法，解决闪烁问题
	 */
	@Override
	public void update(Graphics g) {
		//System.out.println("update调用");
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
	
	private static final long serialVersionUID = 8677561145772123234L;
	private ImageIcon icon;	//声明列表项的图标
	private String text;	//声明列表项的文本
	private Color backColor;
	private Color foreColor;
	public int listItemWidth=120;
	public int listItemHeight=35;
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
		return new Dimension(this.listItemWidth,this.listItemHeight);//列表框中列表项大小
	}
}



public class Sketchpad extends JFrame implements MouseListener,MouseMotionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4670806987716816519L;

	private JPanel 			contentPane;
	
	//文件相关
	public static File				file=null;
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
		//设置点击右上角关闭按钮时不进行关闭操作，而是产生一个window event事件，
		//需要窗口注册winodwListener监听器，并在windowClosing方法中处理
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		//----------------------菜单--------------------
		JMenuBar menuBar = new JMenuBar();//创建一个菜单栏
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");//创建菜单 File
		menuBar.add(mnFile);
		
		JMenuItem mntmNew = new JMenuItem("New");//创建菜单项
		mntmNew.addActionListener(new ActionListener() {//为 New 菜单项添加监听器
			
			@Override
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
		mntmOpen.addActionListener(new ActionListener() {//为 Open 菜单项添加监听器
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!trySave()) {//提醒用户是否需要保存
					return ;
				}
				JFileChooser fileChooser = new JFileChooser();//创建文件选择对话框
				fileChooser.setApproveButtonText("Open");
				fileChooser.removeChoosableFileFilter(fileChooser.getFileFilter());//删除*.*过滤器
				fileChooser.setFileFilter(new MyFileFilterBMP());//添加文件过滤器-------
				fileChooser.setFileFilter(new MyFileFilterPNG());
				fileChooser.setFileFilter(new MyFileFilterJPG());
				fileChooser.setFileFilter(new MyFileFilter());//添加文件类型过滤器-------
				int returntValue = fileChooser.showOpenDialog(getContentPane());//打开文件选择对话框
				if(returntValue==JFileChooser.APPROVE_OPTION) {//判断用户是否选择了文件
					try {
						file =fileChooser.getSelectedFile();//获得文件对象
						System.out.println(file);
						BufferedImage imageTemp = ImageIO.read(file);
						
						/*image=canvas.getImage();
						Graphics2D g2d = (Graphics2D)image.getGraphics();
						g2d.drawImage(imageTemp, 0, 0, DrawCanvas.WIDTH, DrawCanvas.HEIGHT, null);
						canvas.repaint();*///错误的做法 g2d的问题
						
						canvas.drawImage(imageTemp);
						DrawCanvas.isSaved=true;//新建图像之后需要将isSave置为true
						Sketchpad.this.setTitle("Sketchpad - "+file.getName());
					}catch(Exception e1) {
						System.out.println("Error!");
						System.out.println(e1);
					}
				}
			}
		});
		mnFile.add(mntmOpen);
		
		JMenuItem mntmSave = new JMenuItem("Save");
		mntmSave.addActionListener(new ActionListener() {//为 Save 菜单添加监听器
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(trySave());
				
			}
			
		});
		mnFile.add(mntmSave);
		
		JMenuItem mntmSaveAs = new JMenuItem("Save As");
		mntmSaveAs.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//不需要判断 直接进行另存
				canvas.saveAsPicture();
			}
			
		});
		mnFile.add(mntmSaveAs);
		
		JMenu mnEdit = new JMenu("Edit");
		menuBar.add(mnEdit);
		
		JMenuItem mntmUndo = new JMenuItem("Undo");
		mntmUndo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {//为 Undo 菜单添加监听器
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
		mntmRedo.addActionListener(new ActionListener() {//为 Redo 菜单添加监听器
			
			@Override
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
		mntmColor.addActionListener(new ActionListener() {//为 Color 菜单添加监听器
			
			@Override
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
		
		JMenu mnLineStyle = new JMenu("Line Style");
		mnShow.add(mnLineStyle);
		
		//Line type include Solid, Dashed and Dotted 
		JCheckBoxMenuItem chckbxmntmSolid = new JCheckBoxMenuItem("Solid");
		mnLineStyle.add(chckbxmntmSolid);
		chckbxmntmSolid.setState(true);//Line type默认选择Solid
		
		JCheckBoxMenuItem chckbxmntmDashed = new JCheckBoxMenuItem("Dashed");
		mnLineStyle.add(chckbxmntmDashed);
		
		JCheckBoxMenuItem chckbxmntmDotted = new JCheckBoxMenuItem("Dotted");
		mnLineStyle.add(chckbxmntmDotted);
		
		chckbxmntmSolid.addActionListener(new ActionListener() {// 为 Line Style--Solid 菜单项添加监听器


			@Override
			public void actionPerformed(ActionEvent e) {
				canvas.pen.setLineType(LineTypeEnum.SOLID);//设置画笔线形为 Solid
				
				//调整选中状态
				chckbxmntmSolid.setState(true);
				chckbxmntmDashed.setState(false);
				chckbxmntmDotted.setState(false);
			}
			
		});
		chckbxmntmDashed.addActionListener(new ActionListener() {//为 Line Style--Dashed 菜单项添加监听器

			@Override
			public void actionPerformed(ActionEvent e) {
				canvas.pen.setLineType(LineTypeEnum.DASHED);
				
				//调整选中状态
				chckbxmntmSolid.setState(false);
				chckbxmntmDashed.setState(true);
				chckbxmntmDotted.setState(false);
			}
			
		});
		chckbxmntmDotted.addActionListener(new ActionListener() {//为 Line Style--Dotted 菜单项添加监听器

			@Override
			public void actionPerformed(ActionEvent e) {
				canvas.pen.setLineType(LineTypeEnum.DOTTED);
				
				//调整选中状态
				chckbxmntmSolid.setState(false);
				chckbxmntmDashed.setState(false);
				chckbxmntmDotted.setState(true);
			}
			
		});
		
		mnLineStyle.addSeparator();//添加一个分割线
		
		JCheckBoxMenuItem chckbxmntmThin = new JCheckBoxMenuItem("Thin");
		mnLineStyle.add(chckbxmntmThin);
		chckbxmntmThin.setState(true);//default
		
		JCheckBoxMenuItem chckbxmntmMedium = new JCheckBoxMenuItem("Medium");
		mnLineStyle.add(chckbxmntmMedium);
		
		JCheckBoxMenuItem chckbxmntmThick = new JCheckBoxMenuItem("Thick");
		mnLineStyle.add(chckbxmntmThick);
		
		chckbxmntmThin.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				canvas.pen.setSize(1);
				
				chckbxmntmThin.setState(true);
				chckbxmntmMedium.setState(false);
				chckbxmntmThick.setState(false);
			}
			
		});
		chckbxmntmMedium.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				canvas.pen.setSize(3);
				
				chckbxmntmThin.setState(false);
				chckbxmntmMedium.setState(true);
				chckbxmntmThick.setState(false);
			}
			
		});
		chckbxmntmThick.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				canvas.pen.setSize(6);
				
				chckbxmntmThin.setState(false);
				chckbxmntmMedium.setState(false);
				chckbxmntmThick.setState(true);
			}
			
		});
		
		JMenu mnFont = new JMenu("Font");
		mnShow.add(mnFont);
		
		JMenuItem mntmName = new JMenuItem("Name:Default");
		mntmName.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//获取图形化环境
				GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
				//获取系统字体
				String [] fontNames = graphicsEnvironment.getAvailableFontFamilyNames();//第一次会 比较慢
				
				String fontName=(String) JOptionPane.showInputDialog(contentPane,//parent component
						"Please select a font", //tips message
						"Font", //title
						JOptionPane.YES_NO_CANCEL_OPTION,//button style
						null, //Icon
						fontNames,//Object array(selection Values)
						fontNames[0]//initial Selection Value
								);//创建一个输入对话框 让用户选择字体
				if(fontName==null) {
					return;
				}
				mntmName.setText("Name:"+fontName);
				Font oldFont = canvas.pen.getFont();
				Font newFont = new Font(fontName,oldFont.getStyle(),oldFont.getSize());
				canvas.pen.setFont(newFont);
			}
			
		});
		mnFont.add(mntmName);
		
		JMenuItem mntmSize = new JMenuItem("Size:14");
		mntmSize.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Integer [] sizeList = {8,9,10,11,12,14,16,18,20,22,24,26,28,36,48,72};
				Font font = canvas.pen.getFont();//获取当前的字体
				
				Integer size=(Integer) JOptionPane.showInputDialog(
						contentPane,//parent component
						"size", //tips message
						"Font Size", //title
						JOptionPane.YES_NO_CANCEL_OPTION, //button style
						null, //icon
						sizeList, //Object array
						font.getSize()//initial Selection Value
								);//创建对话框 让用户选择字体大小
				if(size==null) {
					return;//点击了取消
				}
				mntmSize.setText("Size:"+size);
				font=font.deriveFont((float)size);
				canvas.pen.setFont(font);
			}
		});
		mnFont.add(mntmSize);
		
		mnFont.addSeparator();//添加分割线
		
		JCheckBoxMenuItem chckbxmntmBold = new JCheckBoxMenuItem("Bold");
		chckbxmntmBold.setState(false);//default
		chckbxmntmBold.addActionListener(new ActionListener() {//为  Bold 菜单添加监听器

			@Override
			public void actionPerformed(ActionEvent e) {
				//当产生此事件时  复选框的State已经被改变
				Font font = canvas.pen.getFont();
				if(chckbxmntmBold.getState()) {//因为当产生此事件时  复选框的State已经被改变  所以这里的判断对改变后的State进行判断
					//加粗
					System.out.println("加粗");
					font=font.deriveFont(font.getStyle()|Font.BOLD);
				}
				else {
					//取消加粗
					System.out.println("取消加粗");
					font=font.deriveFont(font.getStyle()&(~Font.BOLD));
				}
				canvas.pen.setFont(font);
			}
			
		});
		mnFont.add(chckbxmntmBold);
		
		JCheckBoxMenuItem chckbxmntmItalic = new JCheckBoxMenuItem("Italic");
		chckbxmntmItalic.setState(false);//default
		chckbxmntmItalic.addActionListener(new ActionListener() {//为 Italic 菜单添加监听器

			@Override
			public void actionPerformed(ActionEvent e) {
				Font font = canvas.pen.getFont();//获取当前字体
				if(chckbxmntmItalic.getState()) {
					//斜体
					font=font.deriveFont(font.getStyle()|Font.ITALIC);
				}
				else
				{
					//取消 斜体
					font=font.deriveFont(font.getStyle()&(~Font.ITALIC));
				}
				canvas.pen.setFont(font);
			}
			
		});
		mnFont.add(chckbxmntmItalic);
		
		
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		//-------------------左侧工具栏------------------
		JList<Object> list = new JList<Object>();
		list.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				lastSelectIndex=list.locationToIndex(e.getPoint());
			}
		});
		/*list.addListSelectionListener(new ListSelectionListener() {//为左侧工具栏添加事件
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				System.out.println(e);
				lastSelectIndex=e.getLastIndex();//获取用户点击的选中项
			}
		});//不能的到正确的 Index 或者说没有搞懂LastIndex 与FirstIndex的区别
		*/
		IconTextCellRenderImpl cr = new IconTextCellRenderImpl();//实例化渲染器
		//String [] listItem = {"指针","直线","任意线","橡皮","矩形","圆","椭圆","文字"};//列表框项目文本数组
		String [] listItem = PenTypeEnum.getPenTypeList();//从枚举类里获得工具条项目的文本数组
		list.setCellRenderer(cr);//渲染
		list.setModel(new DefaultComboBoxModel<Object>(listItem));
		list.setBackground(new Color(250,250,250));
		contentPane.add(list, BorderLayout.WEST);
		
		//--------------------Canvas-------------------
		//创建一个图像对象，以后就在这上边画了
		image = new BufferedImage(DrawCanvas.CANVAS_WIDTH, DrawCanvas.CANVAS_HEIGHT,
				BufferedImage.TYPE_INT_BGR);
		
		Graphics2D g2d = //仅允许在初始时使用一次---------
				(Graphics2D) image.getGraphics();//获取绘图对象
		g2d.setColor(new Color(255,255,255));//初始填充为白色
		g2d.fillRect(0, 0, DrawCanvas.CANVAS_WIDTH, DrawCanvas.CANVAS_HEIGHT);
		g2d=null;//---------------------------------
		
		canvas = new DrawCanvas(image);//创建画布对象
		canvas.addMouseListener(this);//注册鼠标监听器
		canvas.addMouseMotionListener(this);
		contentPane.add(canvas,java.awt.BorderLayout.CENTER);
		
		this.setBounds(100, 100, DrawCanvas.CANVAS_WIDTH+cr.listItemWidth+18, DrawCanvas.CANVAS_HEIGHT+menuBar.getHeight()+65);
		//System.out.println(this.getBounds());
		//System.out.println(list.getWidth());
	}//end Sketchpad()
	
	/**
	 * @Description: 当用户执行新建、打开新图片、关闭窗口等类似可能丢失数据的操作时。
	 * 应该调用此方法，尝试提醒用户保存图片。
	 * 
	 * @return 	true:图片已保存，可以继续执行后续操作
	 * 			false:用户选择取消or 保存图片失败表示不可及继续执行后续操作
	 */
	public boolean trySave() {
		
		String fileName = null;
		
		if(DrawCanvas.isSaved) {
			if(file!=null) {
				if(file.exists()) {
					//已保存的图	文件没有被删除	不需要保存
					return true;
				}
				else {
					//已保存的图	但是文件被删除	需要保存
					fileName=file.getName();
				}
			}
			else {
				//已保存的图	原始图		不需要保存
				return true;
			}
		}
		else {
			if(file!=null) {
				if(file.exists()) {
					//没保存的图	文件存在	需要保存
					fileName=file.getName();
				}
				else {
					//没保存的图	文件不存在	需要保存
					fileName=file.getName();
				}
			}
			else {
				//没保存的图	原始图		需要另存
				fileName="Untitled";
			}
		}
		
		/*
		if(file!=null) {//判断是否首次打开
			if(file.exists()) {//判断文件是否存在
				if(DrawCanvas.isSaved) {//判断当前图片是否已被改变而且没有保存
					return true;//不需要保存
				}
				else {
					//需要判断是否覆盖
				}
			}
			fileName = file.getName();
		}
		else {
			if(DrawCanvas.isSaved) {
				return true;//不需要保存
			}
			else {
				fileName="Untitled";
			}
			
		}*/
		
		//需要提醒用户是否保存
		//创建一个对话框
		int flag = JOptionPane.showConfirmDialog(null, //parentComponent
				"Do you want to save changes to "+fileName+"?",//message
				"Save",//title
				JOptionPane.YES_NO_CANCEL_OPTION);//按钮选项
		switch(flag) {
		case JOptionPane.YES_OPTION:{//用户选择保存
			//先保存然后流程继续向下
			if(DrawCanvas.isSaved==false&&file==null) {
				//没保存的图	原始图		需要另存
				canvas.saveAsPicture();
			}
			else {
				canvas.savePicture();
			}
			//canvas.saveAsPicture();
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
		//流程从上边流出时，表示用户已经做好选择（已保存or保存）
		this.setTitle("Sketchpad - "+file.getName());
		return true;
	}
	
	//以下为接口的实现----------------------------------------
	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource()==canvas) {
			if(PenTypeEnum.values()[this.lastSelectIndex]==PenTypeEnum.String) {
				x1=e.getX();
				x2=e.getY();
				
				String string = JOptionPane.showInputDialog(contentPane, "Please enter string:");
				if(string==null) {// 点击了取消
					return;
				}
				string=string.strip();
				if("".equals(string)) {//输入为空
					return;
				}
				canvas.drawString(string, x1, y1, x2, y2);
				
				
				DrawCanvas.isSaved=false;
			}
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
				//判断当前选中的工具是否时 任意线 Arbitrary line
				if(PenTypeEnum.values()[this.lastSelectIndex]==PenTypeEnum.Arbitrary_line) {
					canvas.saveStep();
					DrawCanvas.isSaved=false;
					isPressed=false;
				}
				else {
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
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		
		//判断当前选中的工具是否时 任意线 Arbitrary line
		if(PenTypeEnum.values()[this.lastSelectIndex]==PenTypeEnum.Arbitrary_line) {
			x2=e.getX();
			y2=e.getY();
			
			//canvas.pen.setType(PenTypeEnum.values()[this.lastSelectIndex]);
			//canvas.draw(x1, y1, x2, y2);
			canvas.drawArbitraryLine(x1, y1, x2, y2);
			x1=x2;
			y1=y2;
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		//System.out.println(e);
	}

}
