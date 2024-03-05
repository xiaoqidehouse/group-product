
<template>
  <div>

    <span style="font-size: 77px;color: #13ce66">卫星地图</span>

    <div id="container"></div>

  </div>
</template>

<script>
import AMapLoader from '@amap/amap-jsapi-loader';

export default {
  name: "gaode",
  data() {ji
    return {
      map: null //初 始化 map 对象
    }
  },
  methods: {
    ///66666
    initMap() {
      AMapLoader.load({

        key: "56ea2c4462d1fbe8b1992fd6f74da366", //此处填入我们注册账号后获取的Key
        version: "2.0", //指定要加载的 JSAPI 的版本，缺省时默认为 1.4.15
        plugins: [''], //需要使用的的插件列表，如比例尺'AMap.Scale'等

      }).then((AMap) => {

        this.map = new AMap.Map("container", { //设置地图容器id
          viewMode: "3D", //是否为3D地图模式
          zooms: [3, 20], //地图显示的缩放级别范围
          resizeEnable: true, //是否监控地图容器尺寸变化
          zoom: 5, //初始化地图级别
          center: [105.602725, 37.076636], //初始化地图中心点位置

        });
        // 鼠标样式(变手指)
        this.map.setDefaultCursor("pointer");
        // 卫星地图
        var weixing = new AMap.TileLayer.Satellite({
          zIndex:10
        });
        this.map.add(weixing);
      })
        .catch(e => {
        console.log(e);
      })
    },
  },
  mounted() {
    //DOM初始化完成进行地图初始化
    this.initMap();

  }
}
</script>

<style>
#container {
  width: 97%;
  height: 666px;
  margin: 177px auto;
  border: 1px solid #ccc;
  border-radius: 10px;
  box-shadow: 0 0 10px #ddd;
  overflow: hidden;
  font-family: "微软雅黑";
  font-size: 14px;
  position: relative;
  background: #fff;
  z-index: 999;
  display: block;
}
</style>
