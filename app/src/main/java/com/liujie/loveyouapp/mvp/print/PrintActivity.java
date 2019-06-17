package com.liujie.loveyouapp.mvp.print;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import com.liujie.loveyouapp.R;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.PicturesManager;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.usermodel.Picture;
import org.apache.poi.hwpf.usermodel.PictureType;
import org.apache.poi.hwpf.usermodel.Range;
import org.w3c.dom.Document;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 登录
 */
public class PrintActivity extends AppCompatActivity {
    @BindView(R.id.button)
    Button button;


    private static final int MY_PERMISSIONS_REQUEST = 1;
    // 创建生成的文件地址
    private static final String newPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/doc/Name.doc";
    private static final String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/doc";

    private String docName = "Name.doc";
    private String docPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/doc/";
    private String savePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/doc/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        ButterKnife.bind(this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(PrintActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(PrintActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                            MY_PERMISSIONS_REQUEST);
                } else {
                    printer();
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                printer();
            } else {
                Toast.makeText(PrintActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * 为了保证模板的可用，最好在现有的模板上复制后修改
     */
    private void printer() {
        try {
            //从assets读取我们的Word模板
            InputStream is = getAssets().open("Name.doc");
            //创建生成的文件
            File newFile = new File(newPath);
            Map<String, String> map = new HashMap<String, String>();
            map.put("XM", "刘婕");//姓名
            map.put("SFZHM", "142303188425145555");//身份证号
            map.put("KH", "01");//体检编号
            map.put("JZDName", "北京市");//区县
            map.put("DWMC", "口腔医院");//报名单位
            writeDoc(is, newFile, map);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * newFile 生成文件
     * map 要填充的数据
     */
    public void writeDoc(InputStream in, File newFile, Map<String, String> map) {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                file.mkdirs();
            }
            HWPFDocument hdt = new HWPFDocument(in); // Fields fields = hdt.getFields(); // 读取word文本内容
            Range range = hdt.getOverallRange();
            System.out.println(range.text()); // 替换文本内容
            for (Map.Entry<String, String> entry : map.entrySet()) {
                range.replaceText(entry.getKey(), entry.getValue());
            }
            ByteArrayOutputStream ostream = new ByteArrayOutputStream();
            FileOutputStream out = new FileOutputStream(newFile, true);
            hdt.write(ostream); // 输出字节流
            out.write(ostream.toByteArray());
            out.close();
            ostream.close();

            String name = docName.substring(0, docName.indexOf("."));
            try {
                convert2Html(docPath + docName, savePath + name + ".html");
            } catch (Exception e) {
                e.printStackTrace();
            }

//            WebView加载显示本地html文件
            WebView webView = (WebView) this.findViewById(R.id.office);
            WebSettings webSettings = webView.getSettings();
            webSettings.setLoadWithOverviewMode(true);
            webSettings.setSupportZoom(true);
            webSettings.setBuiltInZoomControls(true);
            webSettings.setUseWideViewPort(true);
            /* 设置网页自适应屏幕大小，该属性必须和上一属性配合使用 */
            webSettings.setLoadWithOverviewMode(true);
            /* 设置是否显示水平滚动条 */
            webView.setHorizontalScrollBarEnabled(false);
            //这个位置要注意，加载本地的Html页面一定要使用“file:///”的方式
            webView.loadUrl("file:///" + savePath + name + ".html");

//            String path = SDCardUtils.getSDCardRootDir() + "/hetong.docx";
//            File doc = new File(newPath);
//            Log.e("cccccc", "writeDoc: " + newPath);
//            // ComponentName comp = new ComponentName("com.dynamixsoftware.printershare","com.dynamixsoftware.printershare.ActivityPrintPDF");
//            ComponentName comp = new ComponentName("com.dynamixsoftware.printershare", "com.dynamixsoftware.printershare.ActivityPrintDocuments");
//            Intent intent = new Intent();
//            intent.setComponent(comp);
//            intent.setAction("android.intent.action.VIEW");
//            intent.setType("application/doc");
//            intent.setData(Uri.fromFile(doc));
//            startActivity(intent);


        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * word文档转成html格式
     */
    public void convert2Html(String fileName, String outPutFile) {
        HWPFDocument wordDocument = null;
        try {
            wordDocument = new HWPFDocument(new FileInputStream(fileName));
            WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(
                    DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument());
            //设置图片路径
            wordToHtmlConverter.setPicturesManager(new PicturesManager() {
                public String savePicture(byte[] content, PictureType pictureType, String suggestedName, float widthInches, float heightInches) {
                    String name = docName.substring(0, docName.indexOf("."));
                    return name + "/" + suggestedName;
                }
            });
            //保存图片
            List<Picture> pics = wordDocument.getPicturesTable().getAllPictures();
            if (pics != null) {
                for (int i = 0; i < pics.size(); i++) {
                    Picture pic = (Picture) pics.get(i);
                    try {
                        String name = docName.substring(0, docName.indexOf("."));
                        String file = savePath + name + "/" + pic.suggestFullFileName();
                        File mFile = new File(file);
                        if (mFile.exists()) {
                            mFile.delete();
                        }
                        mFile.mkdirs();
                        pic.writeImageContent(new FileOutputStream(file));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
            wordToHtmlConverter.processDocument(wordDocument);
            Document htmlDocument = wordToHtmlConverter.getDocument();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            DOMSource domSource = new DOMSource(htmlDocument);
            StreamResult streamResult = new StreamResult(out);
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer serializer = tf.newTransformer();
            serializer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
            serializer.setOutputProperty(OutputKeys.INDENT, "yes");
            serializer.setOutputProperty(OutputKeys.METHOD, "html");
            serializer.transform(domSource, streamResult);
            out.close();
            //保存html文件
            writeFile(new String(out.toByteArray()), outPutFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 将html文件保存到sd卡
     */
    public void writeFile(String content, String path) {
        FileOutputStream fos = null;
        BufferedWriter bw = null;
        try {
            File file = new File(path);
            if (!file.exists()) {
                file.createNewFile();
            }
            fos = new FileOutputStream(file);
            bw = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"));
            bw.write(content);
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                if (bw != null) bw.close();
                if (fos != null) fos.close();
            } catch (IOException ie) {
            }
        }
    }

}
