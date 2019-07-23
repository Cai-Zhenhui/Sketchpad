/**
 * @Title: PenTypeEnum.java
 * @Package: 
 * @Description: TODO
 * @author: Cai Zhenhui
 * @date Jul 21, 2019
 */

/**
 * @ClassName: PenTypeEnum
 * @Description: TODO
 * @author: Cai Zhenhui
 * @date: Jul 21, 2019 2:53:06 AM
 *
 */
public enum PenTypeEnum {
	//"指针","直线","任意线","橡皮","矩形","圆","椭圆","文字"
	Pointer,			//指针，用来拖动
	Line,			//直线
	Arbitrary_line,	//任意线
	Eraser,			//橡皮
	Rectangle,		//矩形
	Cricle,			//圆
	Ellipse,		//椭圆
	String;			//文字
	public static String[] getPenTypeList() {
		PenTypeEnum enumtest =PenTypeEnum.Pointer;
		int length=PenTypeEnum.values().length;
		String [] list= new String[length];
		for(int i=0;i<length;++i) {
			list[i]=PenTypeEnum.values()[i].name();
		}
		return list.clone();
	}
}
