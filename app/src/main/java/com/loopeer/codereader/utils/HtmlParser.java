package com.loopeer.codereader.utils;

import android.content.Context;
import android.os.Build;

import com.loopeer.codereader.R;

import java.io.IOException;
import java.io.InputStream;

public class HtmlParser {
    public static String buildHtmlContent(Context context, String paramString1, String jsFile
            , String fileName) {
        for (; ; ) {
            try {
                InputStream inputStream = context.getAssets().open("code.html");
                Object localObject = new byte[inputStream.available()];
                inputStream.read((byte[]) localObject);
                inputStream.close();
                localObject = new String((byte[]) localObject);
                StringBuilder localStringBuilder = new StringBuilder();
                localStringBuilder.append("SyntaxHighlighter.defaults['auto-links'] = false;");
                localStringBuilder.append("SyntaxHighlighter.defaults['toolbar'] = false;");
                localStringBuilder.append("SyntaxHighlighter.defaults['wrap-lines'] = false;");
                localStringBuilder.append("SyntaxHighlighter.defaults['quick-code'] = false;");
                if (!PrefUtils.getPrefDisplayLineNumber(context)) {
                    localStringBuilder.append("SyntaxHighlighter.defaults['gutter'] = false;");
                }
                localStringBuilder.append("SyntaxHighlighter.all();");
                String temp = "";
                if (Build.VERSION.SDK_INT < 14) {
                    temp = "$('.syntaxhighlighter').css('overflow', 'visible !important');";
                }
                jsFile = ((String) localObject)
                        .replace("!FONT_SIZE!"
                                , String.format("<style>.code .syntaxhighlighter { font-size: %.2fpx !important; }</style>"
                                        , new Object[]{Float.valueOf(PrefUtils.getPrefFontSize(context))}))
                        .replace("!FILENAME!"
                                , fileName)
                        .replace("!BRUSHJSFILE!", jsFile)
                        .replace("!SYNTAXHIGHLIGHTER!"
                                , localStringBuilder.toString())
                        .replace("!JS_FIX_HSCROLL!", temp);
                temp = "<link type='text/css' rel='stylesheet' href='style_menlo.css'/>";
                return jsFile
                        .replace("!STYLE_MENLO!", PrefUtils.getPrefMenlofont(context) ? temp : "")
                        .replace("!THEME!", PrefUtils.getPrefTheme(context))
                        .replace("!CODE!", paramString1)

                        .replace("!WINDOW_BACK_GROUND_COLOR!"
                                , ColorUtils.getColorString(context, R.color.code_read_background_color));
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {

            }
        }
    }
}
