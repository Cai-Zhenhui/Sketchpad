import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
//import javax.swing.Scrollable;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.event.*;


/**
 * @ClassName: MyMenu
 * @Description: 菜单栏 
 * @author: Cai Zhenhui
 * @date: Jul 19, 2019 1:46:03 AM
 *
 */
class MyMenu extends JMenuBar{

	private JMenu menuFile;
	JMenuItem menuFileItemNew;
	JMenuItem menuFileItemOpen;
	JMenuItem menuFileItemSave;
	JMenuItem menuFileItemSaveAs;
	private JMenu menuEdit;
	JMenuItem menuEditItemUndo;
	JMenuItem menuEditItemRedo;
	private JMenu menuShow;
	JMenuItem menuShowItemColor;
	JMenuItem menuShowItemLineType;
	JMenuItem menuShowItemFont;
	
	public MyMenu() {
		super();
		initialize();
	}
	private void initialize() {
		//实例化各个菜单
		//一级菜单 文件
		menuFile = new JMenu("File");
		//文件下的 二级菜单
		menuFileItemNew		= new JMenuItem("New");
		menuFileItemOpen	= new JMenuItem("Open");
		menuFileItemSave	= new JMenuItem("Save");
		menuFileItemSaveAs	= new JMenuItem("Save As");
		
		//一级菜单 编辑
		menuEdit = new JMenu("Edit");
		//编辑下的 二级菜单
		menuEditItemUndo	= new JMenuItem("Undo");
		menuEditItemRedo	= new JMenuItem("Redo");
		
		//一级菜单 显示
		menuShow = new JMenu("Show");
		//显示下的 二级菜单
		menuShowItemColor	= new JMenuItem("Color");
		menuShowItemLineType= new JMenuItem("Line Type");
		menuShowItemFont	= new JMenuItem("Font");
		
		//加入菜单
		super.add(menuFile);
		menuFile.add(menuFileItemNew);
		menuFile.add(menuFileItemOpen);
		menuFile.add(menuFileItemSave);
		menuFile.add(menuFileItemSaveAs);
		
		super.add(menuEdit);
		menuEdit.add(menuEditItemUndo);
		menuEdit.add(menuEditItemRedo);
		
		super.add(menuShow);
		menuShow.add(menuShowItemColor);
		menuShow.add(menuShowItemLineType);
		menuShow.add(menuShowItemFont);
		
		this.setVisible(true);//显示
	}
}

/**
 * @ClassName: ToolsList
 * @Description: 工具条类
 * @author: Cai Zhenhui
 * @date: Jul 19, 2019 1:54:53 AM
 *
 */
class ToolsList extends JList{
	
	/**
	 * @ClassName: IconTextCellRenderImpl
	 * @Description: 对列表框的渲染器类  内部类
	 * @author: Cai Zhenhui
	 * @date: Jul 19, 2019 1:55:21 AM
	 *
	 */
	class IconTextCellRenderImpl extends JPanel implements ListCellRenderer{
		
		private ImageIcon icon;	//声明列表项的图标
		private String text;	//声明列表项的文本
		private Color backColor;
		private Color foreColor;

		//实现接口(ListCellRenderer)中的方法
		@Override
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
				boolean cellHasFocus) {
			icon = new ImageIcon("src/icon/"+value+".png");//获取列表项图标
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
			g.drawImage(img,(getWidth()-iconWidth)/2,10,null);//绘制图标
			
			g.drawString(text, getWidth()/2-text.length()*14,iconHeight+40 );//绘制文本
		}
		
		public Dimension getPreferredSize() {
			return new Dimension(80,80);//列表项首选大小
		}
	}
	
	//公有数据成员
	/**
	 * 工具条中当前被选中的项目
	 */
	public int lastSelectedIndex = -1;
	
	//私有数据成员
	private IconTextCellRenderImpl cr;//列表框渲染器类
	private String [] listItem = {"指针","直线","任意线","矩形","圆","椭圆","橡皮"};//列表框项目文本数组
	
	
	public ToolsList() {
		super();
		initialize();
		
	}
	private void initialize() {
		cr = new IconTextCellRenderImpl();//实例化渲染器
		this.setCellRenderer(cr);//渲染
		this.setModel(new DefaultComboBoxModel(listItem));
		
		this.setVisible(true);
	}
	
}


/**
 * @ClassName: DrawPanel
 * @Description: 画布类
 * @author: Cai Zhenhui
 * @date: Jul 19, 2019 5:29:11 AM
 *
 */
class DrawPanel extends Canvas{
	public boolean isPressed=false;
	public int x1,y1,x2,y2=0;
	
	public void DrawLine() {
		
	}
	
	public DrawPanel(){
		super();
		initialize();
	}
	
	private void initialize() {
		this.setBackground(new Color(0,0,0));
		this.setVisible(true);
	}
	
	private void drawLine(Graphics g) {
		// 创建Graphics副本
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setColor(new Color(123, 0, 123));
		g2d.drawLine(x1, y1, x2, y2);
	}
	
	protected void paintComponent(Graphics g) {
		drawLine(g);
	}
}


/**
 * @ClassName: MyFrame
 * @Description: 窗口类
 * @author: Cai Zhenhui
 * @date: Jul 19, 2019 2:31:30 AM
 *
 */
//implements ActionListener
class MyFrame extends JFrame implements ActionListener,MouseListener,ListSelectionListener{
	private final int WINDOW_WIDTH=1080;
	private final int WINDOW_HEIGHT=960;
	
	private MyMenu menubar;
	private ToolsList toolsBar;
	private DrawPanel drawPanel;
	
	
	public MyFrame() {
		super("Sketchpad");
		initialize();
	}
	
	private void initialize() {
		this.setBounds(0, 0,WINDOW_WIDTH, WINDOW_HEIGHT);//set location
		this.setLayout(new BorderLayout());
		//添加组件
		
		//--添加菜单
		menubar = new MyMenu();
		this.setJMenuBar(menubar);
		//为每个菜单项注册监听器
		menubar.menuFileItemNew.addActionListener(this);
		menubar.menuFileItemOpen.addActionListener(this);
		menubar.menuFileItemSave.addActionListener(this);
		menubar.menuFileItemSaveAs.addActionListener(this);
		menubar.menuEditItemUndo.addActionListener(this);
		menubar.menuEditItemRedo.addActionListener(this);
		menubar.menuShowItemColor.addActionListener(this);
		menubar.menuShowItemLineType.addActionListener(this);
		menubar.menuShowItemFont.addActionListener(this);
		
		//--添加工具条
		toolsBar = new ToolsList();
		this.add(toolsBar, java.awt.BorderLayout.WEST);
		toolsBar.addListSelectionListener(this);//注册监听器
		
		//--添加画布
		drawPanel = new DrawPanel();
		this.add(drawPanel,java.awt.BorderLayout.CENTER);
		drawPanel.addMouseListener(this);//注册监听器
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//设置默认关闭
		this.setVisible(true);//设置可视
	}
	
	//鼠标事件监听器
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getSource()==drawPanel) {
			drawPanel.isPressed=true;
			drawPanel.x1=e.getX();
			drawPanel.y1=e.getY();
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getSource()==drawPanel) {
			if(drawPanel.isPressed) {
				drawPanel.x2=e.getX();
				drawPanel.y2=e.getY();
				//drawPanel.
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
	
	//工具条被单击事件监听器
	@Override
	public void valueChanged(ListSelectionEvent e) {
		toolsBar.lastSelectedIndex=e.getLastIndex();//获取被选择的工具的id
		System.out.println(e);
	}
	
	//菜单被单击事件监听器
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		System.out.println(e);
	}


	
}


/**
 * @ClassName: Sketchpad
 * @Description: 主类,程序入口
 * @author: Cai Zhenhui
 * @date: Jul 19, 2019 12:14:05 AM
 *
 */
public class Sketchpad {
	
	public static void main(String [] args) {
		try {
			MyFrame frame = new MyFrame();
		}catch(Exception e) {
			System.out.println("\nOh My God!");
			System.out.println(e);
		}
	}
}
