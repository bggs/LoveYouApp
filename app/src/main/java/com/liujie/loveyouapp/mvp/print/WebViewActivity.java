package com.liujie.loveyouapp.mvp.print;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import com.liujie.loveyouapp.R;
import java.io.File;

public class WebViewActivity extends Activity {
    //文件存储位置
    private String docPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/doc/";
    //文件名称
    private String docName = "Name.doc";
    //html文件存储位置
    private String savePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/doc/";
    private static final String SAVE_SHOT_PATH = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);

//        String name = docName.substring(0, docName.indexOf("."));
//        try {
//            convert2Html(docPath + docName, savePath + name + ".html");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        //WebView加载显示本地html文件
//        WebView webView = (WebView) this.findViewById(R.id.office);
//        WebSettings webSettings = webView.getSettings();
//        webSettings.setLoadWithOverviewMode(true);
//        webSettings.setSupportZoom(true);
//        webSettings.setBuiltInZoomControls(true);
//        webSettings.setUseWideViewPort(true);
//        /* 设置网页自适应屏幕大小，该属性必须和上一属性配合使用 */
//        webSettings.setLoadWithOverviewMode(true);
//        /* 设置是否显示水平滚动条 */
//        webView.setHorizontalScrollBarEnabled(false);
//        //这个位置要注意，加载本地的Html页面一定要使用“file:///”的方式
//        webView.loadUrl("file:///" + savePath + name + ".html");
    }

//    /**
//     * word文档转成html格式
//     */
//    public void convert2Html(String fileName, String outPutFile) {
//        HWPFDocument wordDocument = null;
//        try {
//            wordDocument = new HWPFDocument(new FileInputStream(fileName));
//            WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(
//                    DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument());
//            //设置图片路径
//            wordToHtmlConverter.setPicturesManager(new PicturesManager() {
//                public String savePicture(byte[] content, PictureType pictureType, String suggestedName, float widthInches, float heightInches) {
//                    String name = docName.substring(0, docName.indexOf("."));
//                    return name + "/" + suggestedName;
//                }
//            });
//            //保存图片
//            List<Picture> pics = wordDocument.getPicturesTable().getAllPictures();
//            if (pics != null) {
//                for (int i = 0; i < pics.size(); i++) {
//                    Picture pic = (Picture) pics.get(i);
//                    try {
//                        String name = docName.substring(0, docName.indexOf("."));
//                        String file = savePath + name + "/" + pic.suggestFullFileName();
//                        File mFile = new File(file);
//                        if (mFile.exists()) {
//                            mFile.delete();
//                        }
//                        mFile.mkdirs();
//                        pic.writeImageContent(new FileOutputStream(file));
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//            wordToHtmlConverter.processDocument(wordDocument);
//            Document htmlDocument = wordToHtmlConverter.getDocument();
//            ByteArrayOutputStream out = new ByteArrayOutputStream();
//            DOMSource domSource = new DOMSource(htmlDocument);
//            StreamResult streamResult = new StreamResult(out);
//            TransformerFactory tf = TransformerFactory.newInstance();
//            Transformer serializer = tf.newTransformer();
//            serializer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
//            serializer.setOutputProperty(OutputKeys.INDENT, "yes");
//            serializer.setOutputProperty(OutputKeys.METHOD, "html");
//            serializer.transform(domSource, streamResult);
//            out.close();
//            //保存html文件
//            writeFile(new String(out.toByteArray()), outPutFile);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 将html文件保存到sd卡
//     */
//    public void writeFile(String content, String path) {
//        FileOutputStream fos = null;
//        BufferedWriter bw = null;
//        try {
//            File file = new File(path);
//            if (!file.exists()) {
//                file.createNewFile();
//            }
//            fos = new FileOutputStream(file);
//            bw = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"));
//            bw.write(content);
//        } catch (FileNotFoundException fnfe) {
//            fnfe.printStackTrace();
//        } catch (IOException ioe) {
//            ioe.printStackTrace();
//        } finally {
//            try {
//                if (bw != null) bw.close();
//                if (fos != null) fos.close();
//            } catch (IOException ie) {
//            }
//        }
//    }

    public void startPicturesPrinterShare(String param, Context context) {
//        String param = SAVE_SHOT_PATH + "shot.png";
//        String cls = "com.dynamixsoftware.printershare.ActivityPrintPictures";
//        String type = "image/*";
//        Intent intent = new Intent();
//        ComponentName comp = new ComponentName("com.dynamixsoftware.printershare.amazon", cls);
//        intent = new Intent();
//        intent.setComponent(comp);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.setAction("android.intent.action.VIEW");
//        intent.setDataAndType(Uri.fromFile(new File(param)), type);
//        context.startActivity(intent);
        Intent intent = new Intent();
        ComponentName comp = new ComponentName("com.dynamixsoftware.printershare", "com.dynamixsoftware.printershare.ActivityPrintPDF");
        intent = new Intent();
        intent.setComponent(comp);
        intent.setAction("android.intent.action.VIEW");
        intent.setType("application/pdf");
        intent.setData(Uri.fromFile(new File(param)));
        startActivity(intent);
    }

    public static void Html2Image(String file, String content) {
//        HtmlImageGenerator imageGenerator = new HtmlImageGenerator();
//        Dimension ds = new Dimension(400, 4000);
//        imageGenerator.setSize(ds);
//        String htmlstr = content;
//        imageGenerator.loadHtml(htmlstr);
//        imageGenerator.getBufferedImage();
//        try {
//            ImgUtil.mkdirPath(file);
//            imageGenerator.saveAsImage(file);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        imageGenerator.saveAsHtmlWithMap("hello-world.html", file);


//        HtmlImageGenerator imageGenerator = new HtmlImageGenerator();
//        //加载html模版
//        imageGenerator.loadHtml(htmlTemplate);
//        //把html写入到图片
//        imageGenerator.saveAsImage("hello-world.png");


    }
}
