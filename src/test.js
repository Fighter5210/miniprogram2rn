import React from 'react';
import {
    Dimensions,
    View,
    Text,
    Image,
    Platform,
} from 'react-native';
import Toast from 'react-native-root-toast'

const width = Dimensions.get('window').width;
const height = Dimensions.get('window').height;
import NetUtils from '../DataMgr/NetMgr';

export default class 文件名首字母大写 extends React.Component {

    constructor(props) {
        super(props);
    }

    componentDidMount() {
        // 页面初始化 options为页面跳转所带来的参数
        let arDetail = wx.getStorageSync('ArchiveDetail')

        let detailItem = '', newObj = {}
        if (options && options.detailItem) {
            detailItem = JSON.parse(options.detailItem);
            newObj = this.transLateMoonsAge(detailItem.FSMC)
        }
        wx.setStorageSync("detailItem", detailItem)
        this.setData({
            arDetail: arDetail,
            detailItem: detailItem,
            subAge: newObj.subAge,
            moonsAge: newObj.moonsAge
        })

        wx.setNavigationBarTitle({
            title: newObj.title,
            success: function (res) {
                // success
            }
        });
        // 页面渲染完成
        this.loadData();

    }

    render() {
        const {navigate} = this.props.navigation;
        return (
            '<block wx:if="{{hdEmptyView}}"><!-- 公共部分 start--><view class="archivePanelCss">  <view class="archiveBlock">    <view class="archiveItem">      <text class="leftText">随访日期</text>      <text class="rightText">{{detailItem.TJRQ}}</text>    </view>    <view class="archiveItem">      <text class="leftText">随访医生</text>      <text class="rightText">{{detailItem.SFYSMC}}</text>    </view>    <view class="archiveItem">      <text class="leftText">下次随访日期</text>      <text class="rightText">{{detailItem.XCTJRQ}} </text>    </view>  </view></view><!-- 公共部分 end--><!-- 新生儿部分 start--><block wx:if="{{subAge === '
        newBorn
        '}}">  <view class="archivePanelCss">    <view class="archiveBlock">      <view class="archiveItem">        <text class="leftText">姓名</text>        <text class="rightText">{{arDetail.name}}</text>      </view>      <view class="archiveItem">        <text class="leftText">性别</text>        <text class="rightText">{{arDetail.gender}}</text>      </view>      <view class="archiveItem">        <text class="leftText">出生日期</text>        <text class="rightText">{{arDetail.birthDay}}</text>      </view>      <view class="archiveItem">        <text class="leftText">身份证号</text>        <text class="rightText">{{arDetail.idCard}}</text>      </view>      <view class="archiveItem">        <text class="leftText">家庭住址</text>        <text class="rightText">{{arDetail.presentAddress}}</text>      </view>      <view class="archiveItem">        <text class="leftText">父亲姓名</text>        <text class="rightText">{{detailObj.FQXM}}</text>      </view>      <view class="archiveItem">        <text class="leftText">父亲职业</text>        <text class="rightText">{{detailObj.FQZY}}</text>      </view>      <view class="archiveItem">        <text class="leftText">父亲联系电话</text>        <text class="rightText">{{detailObj.FQLXDH}}</text>      </view>      <view class="archiveItem">        <text class="leftText">父亲出生日期</text>        <text class="rightText">{{detailObj.FQCSRQ}}</text>      </view>      <view class="archiveItem">        <text class="leftText">母亲姓名</text>        <text class="rightText">{{detailObj.MQXM}}</text>      </view>      <view class="archiveItem">        <text class="leftText">母亲职业</text>        <text class="rightText">{{detailObj.MQZY}}</text>      </view>      <view class="archiveItem">        <text class="leftText">母亲联系电话</text>        <text class="rightText">{{detailObj.MQLXDH}}</text>      </view>      <view class="archiveItem">        <text class="leftText">母亲出生日期</text>        <text class="rightText">{{detailObj.MQCSRQ}}</text>      </view>    </view>  </view>  <view class="archivePanelCss">    <view class="archiveBlock">      <view class="archiveItem">        <text class="leftText">出生孕周</text>        <text class="rightText">{{detailObj.CSYZ}}</text>      </view>      <view class="archiveItem">        <text class="leftText">母亲妊娠期患病情况</text>        <text class="rightText">{{detailObj.MQRSQHBQK}} {{detailObj.MQRSQHBQK_OTHER}}</text>      </view>      <view class="archiveItem">        <text class="leftText">助产机构名称</text>        <text class="rightText">{{detailObj.ZCJG}}</text>      </view>      <view class="archiveItem">        <text class="leftText">出生情况</text>        <text class="rightText">{{detailObj.CSQK}} {{detailObj.CSQK_OTHER}}</text>      </view>      <view class="archiveItem">        <text class="leftText">新生儿窒息</text>        <text class="rightText">{{detailObj.XSEZX}}</text>      </view>      <view class="archiveItem" wx:if="{{detailObj.APGARPF}}">        <text class="leftText"></text>        <text class="rightText">{{detailObj.APGARPF}}</text>      </view>      <view class="archiveItem">        <text class="leftText">畸型</text>        <text class="rightText">{{detailObj.SFJXFLAG}} {{detailObj.SFJXYC}}</text>      </view>      <view class="archiveItem">        <text class="leftText">新生儿听力筛查</text>        <text class="rightText">{{detailObj.XSETLSC}}</text>      </view>      <view class="archiveItem">        <text class="leftText">新生儿疾病筛查</text>        <text class="rightText">{{detailObj.XSEJB}} {{detailObj.XSEJB_OTHER}}</text>      </view>      <view class="archiveItem">        <text class="leftText">新生儿出生体重</text>        <text class="rightText">{{detailObj.CSTZ}}</text>      </view>      <view class="archiveItem">        <text class="leftText">目前体重</text>        <text class="rightText">{{detailObj.MQTZ}}</text>      </view>      <view class="archiveItem">        <text class="leftText">出生身长</text>        <text class="rightText">{{detailObj.CSSC}}</text>      </view>      <view class="archiveItem">        <text class="leftText">喂养方式</text>        <text class="rightText">{{detailObj.WYFS}}</text>      </view>      <view class="archiveItem">        <text class="leftText">吃奶量</text>        <text class="rightText">{{detailObj.CNL}}</text>      </view>      <view class="archiveItem">        <text class="leftText">吃奶次数</text>        <text class="rightText">{{detailObj.CNCS}}</text>      </view>      <view class="archiveItem">        <text class="leftText">呕吐</text>        <text class="rightText">{{detailObj.OT}}</text>      </view>      <view class="archiveItem">        <text class="leftText">大便</text>        <text class="rightText">{{detailObj.DB}}</text>      </view>      <view class="archiveItem">        <text class="leftText">大便次数</text>        <text class="rightText">{{detailObj.DBCS}}</text>      </view>    </view>  </view>  <view class="archivePanelCss">    <view class="archiveBlock">      <view class="archiveItem">        <text class="leftText">体温</text>        <text class="rightText">{{detailObj.TW}}</text>      </view>      <view class="archiveItem">        <text class="leftText">心率</text>        <text class="rightText">{{detailObj.ML}}</text>      </view>      <view class="archiveItem">        <text class="leftText">呼吸频率</text>        <text class="rightText">{{detailObj.HXPL}}</text>      </view>      <view class="archiveItem">        <text class="leftText">面色</text>        <text class="rightText">{{detailObj.MSFLAG}} {{detailObj.MSMS}}</text>      </view>      <view class="archiveItem">        <text class="leftText">黄疸部位</text>        <text class="rightText">{{detailObj.HDBW}}</text>      </view>      <!-- 前囟：如果值为不详，其他有值，则不换行显示，否则另起换行 -->      <block wx:if="{{ detailObj.QCZ == '
        不详
        ' && (detailObj.QC || detailObj.QC_OTHER)}}">        <view class="archiveItem">        <text class="leftText">前囟</text>        <text class="rightText">{{detailObj.QC}} {{detailObj.QC_OTHER}}</text>      </view>       </block>      <block wx:else>        <view class="archiveItem">          <text class="leftText">前囟</text>          <text class="rightText">{{detailObj.QCZ}}</text>        </view>        <view class="archiveItem" wx:if="{{detailObj.QC || detailObj.QC_OTHER}}">          <text class="leftText"></text>          <text class="rightText">{{detailObj.QC}} {{detailObj.QC_OTHER}}</text>        </view>       </block>            <view class="archiveItem">        <text class="leftText">眼睛</text>        <text class="rightText">{{detailObj.YBYCFLAG}} {{detailObj.YBYCMS}}</text>      </view>      <view class="archiveItem">        <text class="leftText">四肢活动度</text>        <text class="rightText">{{detailObj.SZHDYCFLAG}} {{detailObj.SZHDYCMS}}</text>      </view>      <view class="archiveItem">        <text class="leftText">耳外观</text>        <text class="rightText">{{detailObj.EBYCFLAG}} {{detailObj.EBYCMS}}</text>      </view>      <view class="archiveItem">        <text class="leftText">颈部包块</text>        <text class="rightText">{{detailObj.JBBKFLAG}} {{detailObj.JBBKMS}}</text>      </view>      <view class="archiveItem">        <text class="leftText">鼻</text>        <text class="rightText">{{detailObj.BBYCFLAG}} {{detailObj.BBYCMS}}</text>      </view>      <view class="archiveItem">        <text class="leftText">皮肤</text>        <text class="rightText">{{detailObj.PIFU}} {{detailObj.PIFU_OTHER}}</text>      </view>      <view class="archiveItem">        <text class="leftText">口腔</text>        <text class="rightText">{{detailObj.KQYCFLAG}} {{detailObj.KQYCMS}}</text>      </view>      <view class="archiveItem">        <text class="leftText">肛门</text>        <text class="rightText">{{detailObj.GMYCFLAG}} {{detailObj.GMYCMS}}</text>      </view>      <view class="archiveItem">        <text class="leftText">心肺听诊</text>        <text class="rightText">{{detailObj.XFYCFLAG}} {{detailObj.XFYCMS}}</text>      </view>      <view class="archiveItem">        <text class="leftText">胸部</text>        <text class="rightText">{{detailObj.XBFLAG}} {{detailObj.XBYCMS}}</text>      </view>      <view class="archiveItem">        <text class="leftText">腹部触诊</text>        <text class="rightText">{{detailObj.FBYCFLAG}} {{detailObj.FBYCMS}}</text>      </view>      <view class="archiveItem">        <text class="leftText">脊柱</text>        <text class="rightText">{{detailObj.JZYCFLAG}} {{detailObj.ZJYCMS}}</text>      </view>      <view class="archiveItem">        <text class="leftText">外生殖器</text>        <text class="rightText">{{detailObj.WSZQYCFLAG}} {{detailObj.WSZQYCMS}}</text>      </view>      <view class="archiveItem">        <text class="leftText">脐带</text>        <text class="rightText">{{detailObj.QDYC}} {{detailObj.QDYC_OTHER}}</text>      </view>    </view>  </view></block><!-- 新生儿部分 end--><!-- 1-11月, 12-35, 3岁-6岁 start--><block wx:else>  <view class="archivePanelCss">    <view class="archiveBlock">      <view class="archiveItem">        <text class="leftText">姓名</text>        <text class="rightText">{{arDetail.name}}</text>      </view>      <view class="archiveItem">        <text class="leftText">月（年龄</text>        <text class="rightText">{{detailObj.NL}}</text>      </view>    </view>  </view>  <view class="archivePanelCss">    <view class="archiveBlock">      <view class="archiveItem">        <text class="leftText">体重</text>        <text class="rightText" wx:if="{{detailObj.TZ && (!detailObj.TGPJBM || detailObj.TGPJBM == '
        不详
        ')}}">{{detailObj.TZ}}</text>        <text class="rightText" wx:elif="{{detailObj.TGPJBM && (!detailObj.TZ || detailObj.TZ == '
        不详
        ')}}">{{detailObj.TGPJBM}}</text>        <text class="rightText" wx:else>{{detailObj.TZ}} {{detailObj.TGPJBM}}</text>      </view>       <view class="archiveItem">        <text class="leftText" wx:if="{{subAge == '
        max8
        '}}">身长</text>        <text class="leftText" wx:elif="{{subAge == '
        max30
        '}}">身长（高）</text>        <text class="leftText" wx:elif="{{subAge == '
        max72
        '}}">身高</text>        <text class="rightText" wx:if="{{detailObj.SG && (!detailObj.TG1PJBM || detailObj.TG1PJBM == '
        不详
        ')}}">{{detailObj.SG}}</text>        <text class="rightText" wx:elif="{{detailObj.TG1PJBM && (!detailObj.SG || detailObj.SG == '
        不详
        ')}}">{{detailObj.TG1PJBM}}</text>        <text class="rightText" wx:else>{{detailObj.SG}} {{detailObj.TG1PJBM}}</text>      </view>      <view class="archiveItem" wx:if="{{subAge == '
        max8
        '}}">        <text class="leftText">头围</text>        <text class="rightText">{{detailObj.TW}}</text>      </view>      <block wx:elif="{{subAge == '
        max72
        '}}">        <view class="archiveItem">          <text class="leftText">身高/体重</text>          <text class="rightText" wx:if="{{detailObj.BMI && (!detailObj.BMIBM || detailObj.BMIBM == '
        不详
        ')}}">{{detailObj.BMI}}</text>          <text class="rightText" wx:elif="{{detailObj.BMIBM && (!detailObj.BMI || detailObj.BMI == '
        不详
        ')}}">{{detailObj.BMIBM}}</text>          <text class="rightText" wx:else>{{detailObj.BMI}} {{detailObj.BMIBM}}</text>        </view>        <view class="archiveItem">          <text class="leftText">体格发育评价</text>          <text class="rightText">{{detailObj.FYPJ}}</text>        </view>      </block>    </view>    <view class='
        borderTop
        '></view>    <view class='
        archiveHeader
        archiveHeaderMargin
        ' bindtap='
        toggleDetailFn
        '>体格检查</view>    <!-- 月龄小于36月，即3岁以下 -->    <view class="archiveBlock noBorder" wx:if="{{moonsAge < 36}}">      <view class="archiveItem">        <text class="leftText">面色</text>        <text class="rightText">{{detailObj.MS}}</text>      </view>       <view class="archiveItem">        <text class="leftText">皮肤</text>        <text class="rightText">{{detailObj.PF}}</text>      </view>      <view class="archiveItem">        <text class="leftText">前囟</text>        <text class="rightText" wx:if="{{moonsAge < 30 ||  moonsAge > 35}}">{{detailObj.CMBH}} {{detailObj.QCZ}}</text>        <!-- 前囟 [30-35] 显示为「杠」 -->        <text class="rightText" wx:else>——</text>      </view>      <view class="archiveItem" wx:if="{{subAge == '
        max8
        '}}">        <text class="leftText">颈部包块</text>        <text class="rightText" wx:if="{{moonsAge < 8 || moonsAge > 11}}">{{detailObj.JBBK}}</text>        <!-- 颈部包块 [8-11] 显示为「杠」 -->        <text class="rightText" wx:else>——</text>      </view>      <view class="archiveItem">        <text class="leftText">眼睛</text>        <text class="rightText">{{detailObj.YWG}}</text>      </view>      <view class="archiveItem">        <text class="leftText" wx:if="{{subAge == '
        max8
        '}}">耳</text>        <text class="leftText" wx:if="{{subAge == '
        max30
        '}}">耳外观</text>        <text class="rightText">{{detailObj.EWG}}</text>      </view>       <view class="archiveItem">        <text class="leftText">听力</text>        <text class="rightText" wx:if="{{ moonsAge == 6 || moonsAge ==7 || (moonsAge > 11 && moonsAge < 18) || (moonsAge > 23 && moonsAge < 30)}}">{{detailObj.TL}}</text>        <!-- 听力 [1-5, 8-11, 18-23, 30-35] 显示为「杠」 -->        <text class="rightText" wx:else>——</text>      </view>       <view class="archiveItem" wx:if="{{subAge == '
        max8
        '}}">        <text class="leftText">口腔</text>        <!-- 部分 6月以下口腔显示为 牙齿数-YCS -->        <text class="rightText" wx:if="{{moonsAge < 6}}">{{detailObj.KQ}}</text>        <text class="rightText" wx:else>出牙数{{detailObj.YCS}}(颗)</text>      </view>      <view class="archiveItem" wx:if="{{subAge == '
        max30
        '}}">        <text class="leftText">出牙/龋齿数（颗）</text>        <text class="rightText">{{detailObj.YCS}}/{{detailObj.YCGS}}</text>      </view>      <view class="archiveItem">        <text class="leftText">胸部</text>        <text class="rightText">{{detailObj.XF}}</text>      </view>      <view class="archiveItem">        <text class="leftText">腹部</text>        <text class="rightText">{{detailObj.FB}}</text>      </view>      <view class="archiveItem" wx:if="{{subAge == '
        max8
        '}}">        <text class="leftText">脐部</text>        <text class="rightText" wx:if="{{moonsAge < 6 || moonsAge > 11}}">{{detailObj.QB}}</text>        <!-- 脐部 [6-11] 显示为「杠」 -->        <text class="rightText" wx:else>——</text>      </view>      <view class="archiveItem">        <text class="leftText">四肢</text>        <text class="rightText">{{detailObj.SZ}}</text>      </view>      <view class="archiveItem" wx:if="{{subAge == '
        max8
        '}}">        <text class="leftText">可疑佝偻病症状</text>        <text class="rightText" wx:if="{{moonsAge > 2}}">{{detailObj.GLBBZ}}</text>        <!-- 可疑佝偻病症状 [1-2] 显示为「杠」 -->        <text class="rightText" wx:else>——</text>      </view>      <view class="archiveItem" wx:if="{{subAge == '
        max30
        '}}">        <text class="leftText">步态</text>        <text class="rightText" wx:if="{{moonsAge < 12 || moonsAge > 17}}">{{detailObj.BT}}</text>        <!-- 步态 [12-17] 显示为「杠」 -->        <text class="rightText" wx:else>——</text>      </view>      <view class="archiveItem">        <text class="leftText">可疑佝偻病体征</text>        <text class="rightText" wx:if="{{moonsAge > 2 && moonsAge < 30}}">{{detailObj.GLBTZ}}</text>        <!-- 可疑佝偻病体征 [1-2, 30-35] 显示为「杠」 -->        <text class="rightText" wx:else>——</text>      </view>      <view class="archiveItem" wx:if="{{subAge == '
        max8
        '}}">        <text class="leftText">肛门/外生殖器</text>        <text class="rightText">{{detailObj.GMWSZQ}}</text>      </view>      <view class="archiveItem">        <text class="leftText">血红蛋白值</text>        <text class="rightText" wx:if="{{(moonsAge > 5 && moonsAge < 12)|| (moonsAge > 17 && moonsAge < 24) || moonsAge > 29}}">{{detailObj.XHDBS}}</text>        <!-- 血红蛋白值 [1-5, 12-17, 24-29] 显示为「杠」 -->        <text class="rightText" wx:else>——</text>      </view>    </view>    <!-- 月龄大等于36月，即3岁以上 -->    <view class="archiveBlock noBorder" wx:else>      <view class="archiveItem">        <text class="leftText">视力</text>        <view class="rightText" wx:if="{{moonsAge > 36}}">          <text wx:if="{{detailObj.SLZ == '
        不详
        ' && detailObj.SLY == '
        不详
        '}}">{{detailObj.SLZ}}</text>          <block wx:else>            <text wx:if="{{detailObj.SLZ && detailObj.SLZ != '
        不详
        '}}">左眼{{detailObj.SLZ}}</text>            <text wx:if="{{detailObj.SLY && detailObj.SLY != '
        不详
        '}}"> 右眼{{detailObj.SLY}}</text>          </block>        </view>        <!-- 视力 [36] 显示为「杠」 -->        <view class="rightText" wx:else>——</view>      </view>      <view class="archiveItem">        <text class="leftText">听力</text>        <text class="rightText" wx:if="{{moonsAge < 48}}">{{detailObj.TL}}</text>        <!-- 听力 [48-72] 显示为「杠」 -->        <text class="rightText" wx:else>——</text>      </view>      <view class="archiveItem">        <text class="leftText">出牙/龋齿数（颗）</text>        <text class="rightText">{{detailObj.YCS}}/{{detailObj.YCGS}}</text>      </view>      <view class="archiveItem">        <text class="leftText">胸部</text>        <text class="rightText">{{detailObj.XF}}</text>      </view>      <view class="archiveItem">        <text class="leftText">腹部</text>        <text class="rightText">{{detailObj.FB}}</text>      </view>      <view class="archiveItem">        <text class="leftText">血红蛋白值</text>        <text class="rightText">{{detailObj.XHDBS}}</text>      </view>      <view class="archiveItem">        <text class="leftText">其他</text>        <text class="rightText">{{detailObj.QT}}</text>      </view>    </view>    <block wx:if="{{moonsAge < 36}}">      <view class='
        borderTop
        '></view>      <view class="archiveBlock">        <view class="archiveItem">          <text class="leftText">户外活动</text>          <text class="rightText">{{detailObj.HWHD}}</text>        </view>         <view class="archiveItem">          <text class="leftText">服用维生素D</text>          <text class="rightText" wx:if="{{moonsAge < 30 ||  moonsAge > 35}}">{{detailObj.FYWSSD}}</text>          <!-- 服用维生素D [30-35] 显示为「杠」 -->          <text class="rightText" wx:else>——</text>        </view>      </view>    </block>  </view>  <view class="archivePanelCss">    <view class='
        archiveHeader
        ' bindtap='
        toggleDetailFn
        '>发育评估</view>    <view class="archiveBlock">      <view class="archiveItem" wx:if="{{moonsAge <= 2}}">        <!-- 发育评估 [1-2] 显示为「杠」 -->        <text class="rightText">——</text>      </view>      <block wx:else>      <view class="archiveItem" wx:if="{{detailObj.FYPG}}">        <text class="leftText">{{detailObj.FYPG}}</text>      </view>      </block>    </view>  </view>  <view class="archivePanelCss">    <view class='
        archiveHeader
        ' bindtap='
        toggleDetailFn
        '>两次随访间患病情况</view>    <view class="archiveBlock">      <view class="archiveItem" wx:if="{{detailObj.LCSFJHBQK || detailObj.CSFY || detailObj.CSFX || detailObj.CSWS}}">        <view class="leftText">          <text class="" wx:if="{{detailObj.LCSFJHBQK}}">{{detailObj.LCSFJHBQK}}</text>          <text class="" wx:if="{{detailObj.CSFY}}">肺炎{{detailObj.CSFY}} </text>          <text class="" wx:if="{{detailObj.CSFX}}">腹泻{{detailObj.CSFX}} </text>          <text class="" wx:if="{{detailObj.CSWS}}">外伤{{detailObj.CSWS}}</text>        </view>      </view>      <view class="archiveItem" wx:if="{{detailObj.QTHBQK}}">        <text class="leftText">其他 {{detailObj.QTHBQK}}</text>      </view>    </view>  </view></block><!-- 1-11月, 12-35, 3岁-6岁 end--><!-- 公共部分 start--><view class="archivePanelCss" wx:if="{{subAge == '
        newBorn
        '}}">  <view class='
        archiveHeader
        ' bindtap='
        toggleDetailFn
        '>转诊</view>  <view class="archiveBlock">    <view class="archiveItem" wx:if="{{detailObj.ZZFLAG}}">      <text class="leftText">转诊建议</text>      <text class="{{detailObj.ZZFLAG == '
        有
        ' ? '
        rightText
        redText1
        ' : '
        rightText
        '}}">{{detailObj.ZZFLAG}}</text>    </view>     <block wx:if="{{detailObj.ZZFLAG == '
        有
        '}}">      <view class="archiveItem">        <view class="leftText">          <view>原因</view>          <view>{{detailObj.ZZYX}}</view>        </view>      </view>      <view class="archiveItem">        <view class="leftText">          <view>机构及科室</view>          <view>{{detailObj.ZZJG}}</view>        </view>      </view>    </block>  </view></view><view class="archivePanelCss" wx:else>  <view class='
        archiveHeader
        ' bindtap='
        toggleDetailFn
        '>转诊建议</view>  <view class="archiveBlock">    <view class="archiveItem" wx:if="{{detailObj.ZZYW}}">      <text class="leftText">转诊建议</text>      <text class="{{detailObj.ZZYW == '
        有
        ' ? '
        rightText
        redText1
        ' : '
        rightText
        '}}">{{detailObj.ZZYW}}</text>    </view>     <block wx:if="{{detailObj.ZZYW == '
        有
        '}}">      <view class="archiveItem">        <view class="leftText">          <view>原因</view>          <view>{{detailObj.ZZYY}}</view>        </view>      </view>      <view class="archiveItem">        <view class="leftText">          <view>机构及科室</view>          <view>{{detailObj.ZZJG}}</view>        </view>      </view>    </block>  </view></view><view class="archivePanelCss" wx:if="{{subAge == '
        newBorn
        '}}">  <view class='
        archiveHeader
        ' bindtap='
        toggleDetailFn
        '>指导</view>  <view class="archiveBlock">    <view class="leftText" wx:if="{{detailObj.XSEZD}}">      <text>{{detailObj.XSEZD}}</text>      <text wx:if="{{detailObj.XSEZD_OTHER}}">({{detailObj.XSEZD_OTHER}})</text>    </view>  </view></view><view class="archivePanelCss" wx:else>  <view class='
        archiveHeader
        ' bindtap='
        toggleDetailFn
        '>指导</view>  <view class="archiveBlock">    <view class="archiveItem" wx:if="{{detailObj.ZD}}">      <view class="leftText">        <text>{{detailObj.ZD}}</text>        <text wx:if="{{detailObj.ZD_OTHER}}">({{detailObj.ZD_OTHER}})</text>      </view>    </view>  </view></view><!-- 公共部分 end--></block><view class="emptyView" hidden="{{hdEmptyView}}">  <image class="emptyImg" src="../../../images/ic-empty.png"></image>  <text class="emptyText">暂无数据</text></view><loading hidden="{{hdLoading}}">正在加载</loading>'
    )
        ;
    }
}

const styles = {
    styles
