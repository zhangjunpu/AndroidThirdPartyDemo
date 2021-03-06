# MPAndroidChart的属性和方法

---

## 一、图表属性

### 1、刷新

`invalidate()`：在chart中调用会使其刷新重绘  
`notifyDataChanged()`：让chart知道它依赖的基础数据已经改变，并执行所有必要的重新计算（比如偏移量，lenged，最大值，最小值...）。在动态添加数据时需要用到。  

### 2、打印日志

`setLogEnable(boolean enabled)`：设置为true将激活chart的logcat输出。但这不利于性能，如果不是必要的，应保持禁用。  

### 3、chart属性

`setBackgroundColor(int color)`：设置背景颜色，将覆盖整个图表视图。此外，背景颜色可以在布局文件.xml中进行设置。  
`setDescription(Description desc)`：设置图表的描述文字，会显示在图表的右下角。  
`setDescriptionColor(int color)`：设置描述文字的颜色。  
`setDescriptionPosition(float x，floaty)`：自定义描述文字在屏幕上的位置(单位是像素)。  
`setDescriptionTypeface(Typeface t)`：设置描述文字的字体。  
`setDescriptionTextSize(float size)`：设置以像素为单位的描述文字，最小6f，最大16f。  
`setNoDataTextDescription(String desc)`：设置当chart为空时显示的描述文字。  
`setDrawGridBackground(boolean enabled)`：如果启用，chart绘图区后面的背景矩形将绘制。  
`setGridBackgroundColor(int color)`：设置网格背景应与绘制的颜色。  
`setDrawBorder(boolean enabled)`：启用/禁用绘制图表边框(chart周围的线)。  
`setBorderColor(int color)`：设置chart边框线的颜色。  
`setBorderWidth(float width)`：设置chart边界线的宽度，单位dp。  
`setMaxVisibleValueCount(int count)`：设置最大可见绘制的chartcount的数量。只在setDrawValues(）设置为true时有效。  
### 4、启用/禁用  手势交互

`setTouchEnabled(boolean enabled)`：启用/禁用与图表的所有可能的触摸交互。  
`setDragEnabled(boolean enabled)`：启用/禁用拖动(平移)图表。  
`setScaleEnabled(boolean enabled)`：启用/禁用缩放图表上的两个轴。  
`setScaleXEnabled(boolean enabled)`：启用/禁用缩放在X轴上。  
`setScaleYEnabled(boolean enabled)`：启用/禁用缩放在Y轴上。  
`setPinchZoom(boolena enabled)`：如果设置为true，没缩放功能。如果false，x轴和y轴可分别放大。  
`setDoubleTapToZoomEnabled(boolean enabled)`：设置为false以禁止通过在其上双击缩放图表。  
`setHighlightPerDragEnabled(boolean enabled)`：设置为true，允许每个图表表面拖过，当它完全缩小突出。默认值：true  
`setHighlightPerTapEnabled(boolean enabled)`：设置为false，以防止值由敲击姿态被突出显示。值仍然可以通过拖动或编程方式突出显示。默认值：true。  

### 5、图表的抛掷/减速

`setDragDecelerationEnabled(boolean enabled`)：如果设置为true，手指滑动抛掷图表后继续减速滚动。默认值：true。  
`setDragDecelerationFrictionCoef(floatcoef)`：减速的摩擦系数在[0；1]区间，数值越高表示速度回缓慢下降，例如，如果将其设置为0，将立即停止。1是一个无效的值，会自动转换至0.9999。  

### 6、高亮

`highlightValues(Highlight[] highs)`：高亮显示值，高亮显示的点击的位置在数据集中的值。设置null或空数组则撤销所有高亮。  
`highlightValue(int xIndex，int dataSetIndex)`：高亮给定xIndex在数据集的值。设置xIndex或dataSetIndex为-1撤销所有高亮。  
`getHighlightd()`：返回一个highlight[]其中包含所有高亮对象的信息，xIndex和dataSetIndex。以java编程方式使得值高亮不会回调onChartValueSelectedListener。  

### 7、选择回调

MPAndroidChart提供了许多用于交互回调的方法  

#### OnChartValueSelectedListener

在点击高亮时回调，让你的类实现该接口并设置对chart进行监听，即可接收回调。  

```Java
public interface OnChartValueSelectedListener {

    /**
     * 当点击图表里面的值
     *
     * @param e 选择的数据集
     * @param h 相应的突出对象
     */
    void onValueSelected(Entry e, Highlight h);

    /**
     * 当没有选择时
     */
    void onNothingSelected();
}
```

### 8、手势回调

#### OnchartGestureListener

可以使得chart与手势操作进行交互

```Java
public interface OnChartGestureListener {

    /**
     * 开始触摸图表时回调 (ACTION_DOWN)
     *
     * @param me
     * @param lastPerformedGesture
     */
    void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture);

    /**
     * 结束触摸图表时回调 (ACTION_UP, ACTION_CANCEL)
     *
     * @param me
     * @param lastPerformedGesture
     */
    void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture);

    /**
     * 长按图表时回调
     *
     * @param me
     */
    void onChartLongPressed(MotionEvent me);

    /**
     * 双击图表时回调
     *
     * @param me
     */
    void onChartDoubleTapped(MotionEvent me);

    /**
     * 单击图表时回调
     *
     * @param me
     */
    void onChartSingleTapped(MotionEvent me);

    /**
     * 根据手势回调
     *
     * @param me1
     * @param me2
     * @param velocityX
     * @param velocityY
     */
    void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY);

    /**
     * 缩放图表时回调
     *
     * @param me
     * @param scaleX scalefactor on the x-axis
     * @param scaleY scalefactor on the y-axis
     */
    void onChartScale(MotionEvent me, float scaleX, float scaleY);

    /**
     * 移动/旋转时调用
     *
     * @param me
     * @param dX translation distance on the x-axis
     * @param dY translation distance on the y-axis
     */
    void onChartTranslate(MotionEvent me, float dX, float dY);
}

```

让你的类实现该接口并设置对chart进行监听，即可接受回调。
`chart.setOnChartGestureListener(this);`

设置了监听器后，chart会根据你的`setXXXEnable()`进行放缩移动等操作。不用在接口方法里对图表进行放缩移动等操作，接口方法可以让你实现其他对应功能，比如说你要打印放缩的是`ScaleX，ScaleY；`

```Java
@Override
void onChartScale(MotionEvent me, float scaleX, float scaleY) {
    Log.i("Scale /Zoom", "ScaleX: " + scaleX + ", ScaleY: " + scaleY);
}
```

### 9、图表自属方法

#### 雷达图（RadarChart）

`setWebLineWidth(float width)`：设置向外放射线条宽度  
`setWebLineWidthInner(float width)`：设置内部环线宽度  
`setSkipWebLineCount(int count)`：设置隐藏count条放射线条  
`setWebAlpha(int alpha)`： 设置背景透明度  
`setWebColor(int color)`：设置向外放射线颜色  
`setWebColorInner(int color)`：设置内部环线颜色  
`setDrawWeb(boolean enabled)`：设置是否显示雷达图  

#### 柱形图（BarChart）

`setDrawValueAboveBar(boolean enabled)`：设置数字显示在柱形条上部或下部  
`setDrawBarShadow(boolean enabled)`：设置是否显示全部柱形条，不填充部分显示灰色  
`setHighlightFullBarEnabled(boolean enabled)`：设置是否高亮显示  
`setFitBars(boolean enabled)`：设置X轴范围两侧柱形条是否显示一半  
 
#### 饼图（PieChart）

`setUsePercentValues(boolean enabled)`：设置图表内值显示为原始值或百分之  
`setHoleColor(int color)`：设置中心圈背景颜色  
`setDrawSlicesUnderHole(boolean enable)`：设置在中心圈下面是否显示切片  
`setDrawHoleEnabled(boolean enabled)`：设置是否显示中心圈部分  
`setCenterText(CharSequence text)`：设置中心圈文字  
`setDrawCenterText(boolean enabled)`：设置是否显示中心圈文字  
`setHoleRadius(final float percent`：设置中心圈半径占整个饼状图半径的百分比，默认50%  
`setTransparentCircleColor(int color)`：设置透明圈的颜色  
`setTransparentCircleRadius(final float percen`：设置透明圈半径占整个饼状图半径的百分比，默认55%  
`setTransparentCircleAlpha(int alpha)`：设置透明圈的透明度  
`setMaxAngle(float maxangle)`：设置饼图的最大角度，90<= maxangle<=360  

## 二、坐标轴

### 1、Document

`AxisBase`是`XAxis`和`YAxis`的父类  
`AxisBase`：所有标签的基类  
`XAxis`：X轴标签设置。只是用setter方法来修改它，不要直接访问公共变量。（对于RadarChart蜘蛛网状图不是所有的Xlabls都适用。）  
`YAxis`：Y轴标签设置和它的条目。只使用setter方法来修改它，不要直接访问公共变量。（对于RadarChart蜘蛛网状图不是所有的Ylabls都适用。 ）在为chart设置data之前，影响轴的值范围的Customizations需要先被应用。  

### 2、概述

下面提及的方法可以适用于两个轴。  
“轴”类允许特定的Style，由以下components/parts组成（可以包含）：  
轴的标签（y轴垂直绘制或X轴水平取向），contain轴的描述值。  
所谓axis-line被直接绘制在标签旁且平行。  
grid-lines在水平方向，且源自每一个轴标签。  
LimitLines允许呈现的特别信息，如边界或限制。  

### 3、控制轴的绘制。

`setEnable(boolean enabled)`：设置轴启用或禁用。如果false，该轴的任何部分都不会被绘制(不绘制坐标轴/便签等)。  
`setDrawGridLines(boolean enabled)`：设置为true，则绘制网格线。  
`setDrawAxisLines(boolean enabled)`：设置为true，则绘制该行旁边的轴线(axis-line)。  
`setDrawLables(boolean enabled)`：设置为true，则绘制轴的标签。  

### 4、修改轴

`setTextColor(int color)`：设置轴标签的颜色。  
`setTextSize(float size)`：设置轴标签的文字大小。  
`setTypeface(Typeface tf)`：设置周标倩的Typeface。  
`setGridColor(int color)`：设置该轴的网格线颜色。  
`setGridLineWidth(float width)`：设置该轴网格线的宽度。  
`setAxisLineColor(int color)`：设置轴线的轴的颜色。  
`setAxisLineWidth(float width)`：设置该轴轴行的宽度。  
`enableGridDashedLine(float lineLength, float spaceLength, float phase)`：启用网格线的虚线模式中得出，比如像这样“- - - -”。  
      `“lineLength”` // 控制虚线段的长度  
      `“spaceLength”` // 控制线之间的空间  
      `“phase”` // 控制线的起始点  

### 5、限制线

两个轴支持LimitLines来呈现特定信息，如边界或限制线。LimitLines加入到YAxis在水平方向上绘制，添加到XAxis在垂直方向绘制。如何通过给定的轴添加和删除LimitLines：  
`addLimitLines(LimitLine l)`：给该轴添加一个新的LimitLine。  
`removeLimitLine(LimitLine l)`：从该轴删除指定的LimitLine。  
`removeAllLimitLines()`：删除所有的LimitLine。  
`getLimitLines()`：获得所有的 LimitLine  
`setDrawLimitLineBehindData(booleanenable)`：控制LimitLines与actual data之间的z-order。如果设置为true，LimitLines绘制在actualdata的后面，否则在其前面。默认值：false。  

### 6、概述

`XAxis`、`YAxis`类是`AxisBase`的一个子类。  

`XAxis`类是所有与水平轴相关的“数据和信息容器”。每个`LineChart`，`BarChart`，`ScateerChart`，`CandleStickChart`和`RadarChart`都有一个`XAxis`对象。`XAxis`对象展示了以`ArrayList<String>`或String[]（“xVals”）形式递交给ChartData对象的数据。得到XAxis的实例：XAxisxAxis = chart.getXAxis()；  

`YAxis`类是一切与垂直轴相关的数据和信息的容器。 每个`LineChart`，`BarChart`，`ScateerChart`，`CandleStickChart`都有`left`和`right`的`YAxis`的对象，分别在左右两边。 但是`RadarChart`只有一个`YAxis`。缺省情况下，图表的两个轴都被启用，并且将被绘制。得到`XAxis`的实例：`YAxis yAxis = chart.getYAxis()；`  

### 7、属性

`isDrawAxisLineEnabled()`：返回坐标轴是否能被绘制（return true/fasle）  
`setCenterAxisLabels(boolean enabled)`：设置标签是否居中  
`isCenterAxisLabelsEnabled()`：返回坐标轴标签是否居中  
`setDrawLabels(boolean enabled)`：设置是否可以绘制文本  
`isDrawLabelsEnabled()`：返回坐标轴是否可以绘制文本  
`setLabelCount(int count)`：设置坐标轴的标签数量，当count > 25时，count = 25；当count < 2时，count = 2  
`setLabelCount(int count,` boolean force)： 设置坐标轴的标签数量， 这个数字是不固定 if(force == false)，只能是近似的。如 果if(force == true)，则确切绘制指定数量的标签，但这样可能导致轴线分布不均匀。  
`isForceLabelsEnabled()`：返回是否强加标签。默认：false  
`getLabelCount()`：返回标签数量  
`setGranularityEnabled(boolean enabled)`：设置是否可以设置间隔  
`isGranularityEnabled()`：返回是否可以设置间隔  
`getGranularity()`：返回坐标轴间隔大小  
`setGranularity (float granularity)`：设置坐标轴间隔大小   
`getLongestLabel()`：返回坐标轴最长的文本，String类型  
`getFormattedLabel(int index)`：返回坐标轴的格式化文本，String类型  
`setValueFormatter(IAxisValueFormatter f)`：设置坐标轴文本的格式  
`getAxisMaximum()`：返回坐标轴的最大值，float类型  
`getAxisMinimum()`：返回坐标轴的最小值，float类型  
`setAxisMinimum(float min)`：设置坐标轴的最小值  
`setAxisMaximum(float max)`：设置坐标轴的最大值  
`calculate(float dataMin,` float dataMax)：计算坐标轴的最大值和最小值的差值  
`setSpaceMin(float mSpaceMin)`：设置坐标轴额外的最小的空间  
`setSpaceMax(float mSpaceMax)`： 设置坐标轴额外的最大的空间  
`setAvoidFirstLastClipping(boolean enabled)`:  设置X轴第一个和最后一个标签超出屏幕  
`setLabelRotationAngle(float angle)`：设置X轴标签文字的方向  
`setPosition(XAxisPosition pos)`：设置X轴标签的位置  
`setZeroLineWidth(float width)`：设置Y轴第一条线的宽度  
`setZeroLineColor(int color)`：设置Y轴第一条线的颜色  
`setDrawZeroLine(boolean mDrawZeroLine)`：设置Y是否显示第一条线  
`setSpaceBottom(float percent)`：设置底部距离  
`setSpaceTop(float percent)`：设置顶部距离  
`setStartAtZero(boolean startAtZero)`：设置Y轴是否从0开始  
`setInverted(boolean enabled)`：设置Y轴是否翻转  
`setDrawTopYLabelEntry(boolean enabled)`：设置是否显示顶部标签  
`setPosition(YAxisLabelPosition pos)`：设置Y轴标签位置  

## 三、数据（DataSet）

`DataSet` 类是所有数据集类的基类，是 `Chart` 中一组或一类的 `Entry` 的集合。 它被设计成 `Chart` 内部逻辑上分离的不同值组（例如， `LineChart` 中特定行的值或 `BarChart` 中特定bar组的值）。  

### 1、属性

`setMode(LineDataSet.Mode mode)`：设置模式  
      `CUBIC_BEZIER` 立方曲线  
      `LINEAR` 直线  
      `STEPPED` 阶梯  
      `HORIZONTAL_BEZIER` 水平曲线  
`setCubicIntensity(float intensity)`：设置曲线的弯曲程度  
`getColors()`：返回颜色（LineChart：折线；BarChart：柱图等），List<Integer>类型  
`getValueColors()`：返回文本颜色， List<Integer>类型  
`getColor()`：返回索引为0的颜色，int类型  
`getColor(int index)`：返回索引为index的颜色，int类型  
`setColors(List<Integer> colors)/setColors(int...colors)/setColors(int[] colors, Context c)`：设置颜色  
`setLabel(String label)`：设置文本  
`getLabel()`：获得文本，String类型  
`setHighlightEnabled(boolean enabled)`：设置为true，允许通过点击高亮突出 ChartData 对象和其 DataSets   
`setValueFormatter(IValueFormatter f)`：设置文本格式  
`setValueTextColor(intcolor)/setValueTextColors(List<Integer> colors)`：设置文本颜色  
`setValueTypeface(Typeface tf)`：设置文本的字体  
`setValueTextSize(float size)`：设置文本的字体大小  
`setForm(Legend.LegendForm form)`：设置形状的大小  
`setFormLineWidth(float formLineWidth)`：设置线的宽度  
`setDrawValues(boolean enabled)`：设置是否显示文本  
`setVisible(boolean visible)`：设置是否显示  
`setDrawFilled(boolean filled)`：设置是否填充  
`setFillAlpha(intalpha)`：设置填充透明度  
`setFillColor(int color)`：设置填充颜色  
`setFillDrawable(Drawable drawable)`：设置填充drawable  
`setDrawCircleHole(boolean enabled)`：设置是否实心  
`removeFirst()`：移除第一个值  
`removeEntry(int index)`：移除下标index的值   
`removeLast()`：移除最后一个值  
`removeEntryByXValue(float xValue)`：根据值移除，不建议使用  

## 四、Legend

`Legend` 通常由一个标签的形式/形状来表示多个条目(`entries`)的每一个。  

`Entries` 数量自动生成的`legend` 取决于 `DataSet` 的标签 不同颜色的数量（在所有 `DataSet` 的对象）。 `Legend` 的标签取决于图表中所使用的 `DataSet`对象。如果没有为 `DataSet` 对象指定标签，图表将自动生成它们。如果多个颜色用于一个 `DataSet` ，这些颜色分类，只通过一个标签说明。  

### 1、属性

`setXOffset(floatxOffset)`：设置在X轴方向的偏移  
`setYOffset(floatyOffset)`：设置在Y轴方向的偏移  
`setTypeface(Typefacetf)`：设置文本的字  
`setTextSize(floatsize)`：设置文本字体大  
`setTextColor(intcolor)`：设置文本颜  
`setEnabled(booleanenabled)`：设置是否可用（简单理解为是否显示  
`setEntries(List<LegendEntry> entries)`：设置图例，传LegendEntry的集  
`setExtra(LegendEntry[]entries)`：设置图例，传LegendEntry数  
`setExtra(List<Integer>colors, List<String> labels)`：设置图例，传color的集合和  LegendEntry的集  
`setExtra(int[]colors, String[] labels)`：设置图例，传color的数字和LegendEntry数  
`setCustom(LegendEntry[] entries)`：设置图例，灰色图标不可见  
`setCustom(List<LegendEntry> entries)`：设置图例，灰色图标不可见  
`setHorizontalAlignment(LegendHorizontalAlignment value)`：设置水平对齐方式  
`setVerticalAlignment(LegendVerticalAlignment value)`：设置垂直对齐方式  
`setOrientation(LegendOrientation value)`：设置方向  
`setDrawInside(boolean value)`：设置是否画在图表里  
`setDirection(LegendDirection pos)`：设置文字的方向  
`setForm(LegendForm shape)`：设置形状  
`setFormSize(float size)`：设置形状大小  
`setFormLineWidth(float size)`：设置线条宽度（形状为线状时）  
`setFormLineDashEffect(DashPathEffect dashPathEffect)`：设置线状轨迹  
`setXEntrySpace(float space)`：设置图例水平方向的间距  
`setYEntrySpace(float space)`：设置图例垂直方向的间距  
`setFormToTextSpace(float space)`：设置图例和文字的间距  
`setWordWrapEnabled(boolean enabled)`：设置图例是否重新创建一行  



