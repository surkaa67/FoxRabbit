# TheFoxAndRabbit
这是一个狐狸吃兔子的地图，参考[mooc翁恺老师的视频](https://www.icourse163.org/learn/ZJU-1001542001?tid=1467810462#/learn/content?type=detail&id=1249310254)  
## 展示
![FoxAndRabbit](https://github.com/surkaa/FoxRabbit/blob/main/src/res/FoxAndRabbit.gif)  
简单介绍：为了更容易的区分，狐狸设置为了`红色`，兔子是`黄色`。颜色的`深浅`表示动物的`寿命`
# 地图规则
## 捕食
总所周知：兔子吃草，狐狸吃兔子，所以我们的这两种动物都有一种行为`捕食`（hunt），当然兔子吃的草还没有模拟出来。
其次是动物就会有寿命，这里我们定义每step一次地图所有的生物寿命都将加一，到指定的寿命限制后将会清除该生物。这里我们设置狐狸的寿命有15，兔子的寿命有10。
## 生育
既然有寿命，要使地图上的生物延续的更长久，那么就得breed出小动物，新的行为`生育`（breed）。
而生小动物肯定得需要不少营养，于是定义了energy来模拟每个动物的体力，当动物捕食的时候相应增加一定的体力，当动物breed小动物时会扣除一定的energy。
还有一件事，不是所有年龄段都能breed小动物，所以我们定义了一个getVitality函数获取其活力，以供breed行为发生做限制。
## 移动
其次是兔子就都会跑，当然狐狸也一样，于是我们又定义了一种行为`移动`（move）。
移动不是随意的，需要消耗体力的，每次移动都会减少一定的体力。
这里为了不让动物有机会能无限的移动限制了energy的范围是0-1。
