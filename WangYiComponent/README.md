# WangyiComponent
/**
 * 组件化需要注意的地方：
 *      1、各个模块的gradle用到的公共变量定义到gradle.properties
 *      2、注意application和library的切换
 *      3、library不能有applicationId
 *      4、library和application的manifest文件是不同的
 *      5、app主模块依赖其他模块的时候，注意其他模块是否是library，application是不能依赖的
 *      6、各个模块的application初始化，需要在app主模块的application中利用反射进行
 */
