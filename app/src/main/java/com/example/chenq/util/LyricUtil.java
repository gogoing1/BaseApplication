package com.example.chenq.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * create by chenqi on 2020/6/9
 * Email: chenqwork@gmail.com
 * Desc: 歌词工具类
 */
public class LyricUtil {

    private static final String TAG = "LyricFactory";
    private static final String DECODE_TYPE = "GBK";
    private static LyricUtil lyricFactory;
    private LyricInfo mLyricInfo;

    private LyricUtil() {
    }

    public static LyricUtil getInstance() {
        if (lyricFactory == null) {
            lyricFactory = new LyricUtil();
        }
        return lyricFactory;
    }

    /**
     * 解析歌词文件
     *
     * @param fileUrl
     */
    public LyricInfo parse(String fileUrl) {
        reSet();
        if (fileUrl.isEmpty()) {
            LogUtil.e(TAG, "parse error,the lyric fileUrl is empty");
            return null;
        }

        File file = new File(fileUrl);
        if (file != null && file.exists() && !file.isDirectory()) {
            LyricInfo lycInfo = new LyricInfo();
            try {
                // read
                FileInputStream fis = new FileInputStream(file);
                InputStreamReader isr = new InputStreamReader(fis, DECODE_TYPE);
                BufferedReader br = new BufferedReader(isr);
                String line;
                while ((line = br.readLine()) != null) {
                    analyzeLyric(lycInfo, line);
                }

                // close
                br.close();
                fis.close();
                isr.close();

                mLyricInfo = lycInfo;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            LogUtil.e(TAG, "parse LyricFile on File not found Exception.");
        }
        return mLyricInfo;
    }

    private void reSet() {
        mLyricInfo = null;
    }

    /**
     * lyc 文件解析
     *
     * @param lyricInfo
     * @param line
     */
    private void analyzeLyric(LyricInfo lyricInfo, String line) {
        int index = line.lastIndexOf("]");
        if (line.startsWith("[offset:")) {
            lyricInfo.songOffset = Long.parseLong(line.substring(8, index).trim());
        } else if (line.startsWith("[ti:")) {
            lyricInfo.songTitle = line.substring(4, index).trim();
        } else if (line.startsWith("[ar:")) {
            lyricInfo.songArtist = line.substring(4, index).trim();
        } else if (line.startsWith("[al:")) {
            lyricInfo.songAlbum = line.substring(4, index).trim();
        } else if (line.startsWith("[by:")) {
            lyricInfo.lycCollectBy = line.substring(4, index).trim();
        } else {
            if (index >= 9 && line.trim().length() > index + 1) {
                LineInfo lineInfo = new LineInfo();
                lineInfo.content = line.substring(10);
                lineInfo.start = getStartTimeMillis(line.substring(0, index));
                lyricInfo.songLines.add(lineInfo);

                //LogUtil.e(TAG,"start:" + lineInfo.start + "  content:" + lineInfo.getContent());
            }
        }
    }

    /**
     * 计算当前时间戳的时间长度
     *
     * @param time
     * @return 单位毫秒
     */
    private long getStartTimeMillis(String time) {
        long m = Long.parseLong(time.substring(1, 3));
        long s = Long.parseLong(time.substring(4, 6));
        long ms = Long.parseLong(time.substring(7, 9));
        return ms + s * 1000L + m * 60L * 1000L;
    }

    /**
     * 获取当前解析的歌词对象
     *
     * @return
     */
    public LyricInfo getLyricInfo() {
        return mLyricInfo;
    }

    /**
     * 获取当前播放行
     *
     * @param info          歌词{@link LyricUtil.LyricInfo}
     * @param curPlayMillis 播放进度（毫秒）
     * @return
     */
    public String getCurLyricContent(LyricInfo info, long curPlayMillis) {
        if (info == null) {
            LogUtil.e(TAG, "getCurLyricContent lyricInfo is empty.");
            return null;
        }
        List<LyricUtil.LineInfo> lineList = info.getSongLines();
        for (int i = 0; i < lineList.size(); i++) {
            if (i + 1 < lineList.size()) {
                if (curPlayMillis >= lineList.get(i).getStart() && curPlayMillis < lineList.get(i + 1).getStart()) {
                    return lineList.get(i).getContent();
                }
            } else {
                return lineList.get(i).getContent();
            }
        }
        return null;
    }


    /**
     * 歌词 Bean
     */
    public class LyricInfo {
        private long songOffset;
        private String songTitle;
        private String songArtist;
        private String songAlbum;
        private String lycCollectBy;
        private List<LineInfo> songLines = new ArrayList<>();

        public LyricInfo() {
        }

        public void setSongOffset(long songOffset) {
            this.songOffset = songOffset;
        }

        public void setSongTitle(String songTitle) {
            this.songTitle = songTitle;
        }

        public void setSongArtist(String songArtist) {
            this.songArtist = songArtist;
        }

        public void setSongAlbum(String songAlbum) {
            this.songAlbum = songAlbum;
        }

        public void setLycCollectBy(String lycCollectBy) {
            this.lycCollectBy = lycCollectBy;
        }

        public void setSongLines(List<LineInfo> songLines) {
            this.songLines = songLines;
        }

        public long getSongOffset() {
            return songOffset;
        }

        public String getSongTitle() {
            return songTitle;
        }

        public String getSongArtist() {
            return songArtist;
        }

        public String getSongAlbum() {
            return songAlbum;
        }

        public String getLycCollectBy() {
            return lycCollectBy;
        }

        public List<LineInfo> getSongLines() {
            return songLines;
        }
    }

    /**
     * 行信息 Bean
     */
    public class LineInfo {
        private String content;
        private long start;

        private LineInfo() {
        }

        public void setContent(String content) {
            this.content = content;
        }

        public void setStart(long start) {
            this.start = start;
        }

        public String getContent() {
            return content;
        }

        public long getStart() {
            return start;
        }
    }
}
