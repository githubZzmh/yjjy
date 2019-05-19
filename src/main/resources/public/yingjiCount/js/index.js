var server = '';
var echarts_timeLine;
var echarts_qipao;
var echartsBar = [];

Vue.use(VueAwesomeSwiper);
// 计算轮播图高度
var wheight = document.body.clientHeight;
var swiperHeight = wheight - 40 -10 -60 -3 -20;
var swiperLineCount = parseInt(swiperHeight/31);
swiperLineCount = swiperLineCount < 20?swiperLineCount:20;
document.getElementById("swiperWrapper").style.height = swiperLineCount*31+'px';
var barheight = wheight - 40 - 40 -30-6-20-260 -10;
console.log(barheight)
document.getElementById("deviceCallBar").style.height = barheight+'px';
document.getElementById("deviceDropBar").style.height = barheight+'px';
var pageVM = new Vue({
	el: '#pageVM',
	components: {
		LocalSwiper: VueAwesomeSwiper.swiper,
		LocalSlide: VueAwesomeSwiper.swiperSlide,
	},
	data: {
		dateStr: '',
		swiperOption: {
			direction: 'vertical',
			slidesPerView: swiperLineCount, //显示3条数据
			slidesPerGroup: 1, //每次轮转1条数据
			loop: true,
			autoplay: {
				delay: 2000, //2秒切换一次
			},
			observer: true //修改自己或子元素时，自动初始化swiper
		},
		curTab :'echartsCall',
		ed:{
			echartsDevice:{
				device_all:0,
				device_online:0,
				rate_crash:0,
				rate_online:0
			},
			listEchartsList:[]
		}
		
	},
	computed: {
		swiperA: function() {
			return this.$refs.awesomeSwiperA.swiper
		},
		swiperB: function() {
			return this.$refs.awesomeSwiperB.swiper
		}
	},
	created: function() {},
	mounted: function() {
		var _this = this;
		/* 获取时间 */
		_this.getDate();
		setInterval(function(){
			_this.getDate()
		},1000);
		
		_this.getData();
	},
	methods: {
		getDate: function() {
			var myDate = new Date();
			var week = {
				'0': '星期天',
				'1': '星期一',
				'2': '星期二',
				'3': '星期三',
				'4': '星期四',
				'5': '星期五',
				'6': '星期六',
			};
			var month = myDate.getMonth() + 1;
			var date = myDate.getDate();
			var minutes = (myDate.getMinutes());
			this.dateStr = myDate.getFullYear() + '/' + (month<10?('0'+month):month) + '/' + (date<10?('0'+date):date) + ' ' + week[myDate.getDay()] + ' ' + myDate.getHours() + ':' + (minutes<10?('0'+minutes):minutes)
		},
		getData: function() {
			var _this = this;
			$.ajax({
                type:"GET",
                url:"http://localhost:9998/yjjy/dp/getMySeat", //访问的链接
                dataType:"jsonp",  //数据格式设置为jsonp
                jsonp:"callback",  //Jquery生成验证参数的名称
                success:function(data){  //成功的回调函数
                    _this.ed = data.ed;
					_this.curTab = 'echartsCall';
					_this.initCountsLine();
					_this.initBar(0);
					_this.initBar(1);
                },
                error: function (e) {
                    alert("error");
                }
            })
			/*$.post("http://localhost:9998/yjjy/dp/getMySeat", {
				//参数
			}, function(res) {
				//res数据在html文件夹中引入的reqdata.js中，对接接口时需去掉
					_this.ed = res.ed;
					_this.curTab = 'echartsCall';
					_this.initCountsLine();
					_this.initBar(0);
					_this.initBar(1);
				}
			})*/
		},
		initBar:function(index){
			var _this = this;
			var dataIndex = [{
				id:'deviceCallBar',
				color:['#fec42c'],
				x:_this.ed.echartsCall.device,
				y:_this.ed.echartsCall.data
			},{
				id:'deviceDropBar',
				color:['#dd4444'],
				x:_this.ed.echartsDrop.device,
				y:_this.ed.echartsDrop.data_
			}]
			echartsBar[index] = echarts.init(document.getElementById(dataIndex[index].id));
			var data = [];
			var option = {
				color:dataIndex[index].color,
				tooltip: {
					trigger: 'axis',
					axisPointer: {
						type: 'cross'
					}
				},
				grid: {
					top: 25,
					right: 10,
					bottom: 45,
					left: 40
				},
				dataZoom: [{
					type: 'slider',
					height: 15,
					bottom:5,
					borderColor:'#294470',
					textStyle:{
						color:'#92C9FB'
					}
				}],
				xAxis: {
					type:'category',
					silent: false,
					data: dataIndex[index].x,
					axisLine: {
						show: true,
						lineStyle: {
							color: '#4B84CE'
						}
					},
					splitLine: {
						show: false
					},
					axisTick: {
						show: false
					},
					axisLabel: {
						verticalAlign: 'top',
						lineHeight: 10,
						color: '#73CBFD'
					}
				},
				yAxis: {
					type: 'value',
					axisLine: {
						show: false,
						lineStyle: {
							color: '#4B84CE'
						}
					},
					axisLabel: {
						color: '#73CBFD'
					},
					splitLine: {
						show: false
					},
					axisTick: {
						show: false
					}
				},
				series: [{
					name: '呼叫次数',
					type:'bar',
					barWidth:10,
					data: dataIndex[index].y,
				}]
			}
			
			//初始化柱状图
			echartsBar[index].setOption(option);
		},
		initCountsLine:function(){
			var _this = this;
			var dangjiData = [];
			var date = [];
			var hujiaoData = [];
			_this.ed.listEchartsDate.map(function(edata){
				date.push(edata.date);
				dangjiData.push(edata.data_dangji);
				hujiaoData.push(edata.data_hujiao);
			})
			echarts_timeLine = echarts.init(document.getElementById('timeChart'));
			var option = {
				color: ['#fec42c','#dd4444'],
				tooltip: {
					trigger: 'axis',
					axisPointer: {
						type: 'cross'
					}
				},
				grid: {
					top: 25,
					right: 20,
					bottom: 45,
					left: 40
				},
				dataZoom: [{
					type: 'slider',
					height: 15,
					bottom:5,
					borderColor:'#294470',
					textStyle:{
						color:'#92C9FB'
					}
				}],
				xAxis: {
					type: 'category',
					data: date,
					axisLine: {
						show: true,
						lineStyle: {
							color: '#4B84CE'
						}
					},
					splitLine: {
						show: false
					},
					axisTick: {
						show: false
					},
					axisLabel: {
						verticalAlign: 'top',
						lineHeight: 10,
						color: '#73CBFD'
					}
				},
				yAxis: {
					type: 'value',
					axisLine: {
						show: false,
						lineStyle: {
							color: '#4B84CE'
						}
					},
					axisLabel: {
						color: '#73CBFD'
					},
					splitLine: {
						show: false
					},
					axisTick: {
						show: false
					},
				},
				series: [{
					name:'呼叫次数',
					data: hujiaoData,
					type: 'line'
				},{
					name:'宕机次数',
					data: dangjiData,
					type: 'line'
				}]
			};
			echarts_timeLine.setOption(option)
		},
		initChartsLine: function() {
			var _this = this;
			echarts_qipao = echarts.init(document.getElementById('echartQipao'));
			var data = [];
			var option = {
				color:['#dd4444', '#fec42c', '#80F1BE'],
				legend: {
			        x: 'center',
			        data: ['1990', '2015']
			    },
			    xAxis: {
					type:'category',
			        splitLine: {
			            lineStyle: {
			                type: 'dashed'
			            }
			        }
			    },
			    yAxis: {
			    		type:'time',
			        splitLine: {
			            lineStyle: {
			                type: 'dashed'
			            }
			        },
			        scale: true
			    },
			    series: [{
			        name: '1990',
			        data: data[0],
			        type: 'scatter',
			        symbolSize: function (data) {
			            return Math.sqrt(data[2]) / 5e2;
			        },
//			        label: {
//			            emphasis: {
//			                show: true,
//			                formatter: function (param) {
//			                    return param.data[3];
//			                },
//			                position: 'top'
//			            }
//			        },
			        itemStyle: {
			            normal: {
			                shadowBlur: 10,
			                shadowColor: 'rgba(120, 36, 50, 0.5)',
			                shadowOffsetY: 5,
			                color: new echarts.graphic.RadialGradient(0.4, 0.3, 1, [{
			                    offset: 0,
			                    color: 'rgb(251, 118, 123)'
			                }, {
			                    offset: 1,
			                    color: 'rgb(204, 46, 72)'
			                }])
			            }
			        }
			    }]
			}
		}

	}
})
