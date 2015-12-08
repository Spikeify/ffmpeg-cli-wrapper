package com.spikeify.ffmpeg;

import com.google.gson.Gson;
import com.spikeify.ffmpeg.probe.FFmpegProbeResult;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class FFprobeTest {

	final static String MEDIA_PATH = "/Users/hiphop/Documents/stitching/";
	final static Gson gson = new Gson();

	FFprobe ffprobe;

	@Before
	public void before() {
		ffprobe = new FFprobe();
	}

	@Test
	public void testProbe() throws IOException {
		FFmpegProbeResult info = ffprobe.probe(MEDIA_PATH + "livecaptv.mp4");
		System.out.println(info.format.duration);
		System.out.println(gson.toJson(info));
	}

}
