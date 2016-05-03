/*--
 Copyright (c) 2013 哈尔滨亿时代数码科技开发有限公司（www.hrbesd.com）. All rights reserved.
  
  HRBESD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
--*/
/*
 * 配置文档
 * */

var toolbarConfig = {};

toolbarConfig.speak = {};
toolbarConfig.speak.alt0 = "mp3/alt_0.mp3";//提示按Alt+0开启工具栏
toolbarConfig.speak.shift0 = "mp3/shift_0.mp3";//提示shift+0朗读工具栏
toolbarConfig.speak.shiftz = "mp3/shift_z.mp3";//提示shift+z朗读工具栏
toolbarConfig.speak.intordus = "mp3/intordus_toolbar.mp3";//朗读工具栏
toolbarConfig.speak.description = "mp3/description_toolbar.mp3";//朗读工具栏
toolbarConfig.speak.fullscreen = "mp3/fullscreen.mp3";//请按F11切换全屏模式
toolbarConfig.speak.swf = "swf/soundmanager2.swf";//soundmanager2Flash路径
toolbarConfig.speak.BatchUrl = "http://voice.yunmd.net/ws/batch";//无障碍云批量转换路径
toolbarConfig.speak.ajaxUrl = "http://voice.yunmd.net/ws/t2s";//无障碍云转换MP3路径
//toolbarConfig.speak.ajaxUrl = "http://voice.yunmd.net/ws/textToSound";//百度语音无障碍云转换MP3路径

toolbarConfig.trans = {};
toolbarConfig.trans.ajaxUrl = "http://voice.yunmd.net/baidu-translate-r1.0/translate";//无障碍云翻译路径
