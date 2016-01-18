package com.spikeify.ffmpeg;

import com.spikeify.ffmpeg.builder.FFmpegBuilder;
import com.spikeify.ffmpeg.builder.elements.*;
import com.spikeify.ffmpeg.job.FFmpegJob;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class StitcherBuilderTest {

	FFmpeg ffmpeg = new FFmpeg();
	FFprobe ffprobe = new FFprobe();

	public StitcherBuilderTest() throws IOException {
	}

	@Test
	public void testStitchVideos() throws IOException {
		//initialize videoObjectsList
		String font = getClass().getResource("Roboto-Regular.ttf").getPath();
		String input = getClass().getResource("video.mp4").getPath();
		String output = input.substring(0, input.lastIndexOf(File.separator)) + File.separator + "output.mp4";
		List<VideoObject> videoObjectList = new ArrayList<>();

		//settings for video 1
		TextBox textBox1 = new TextBox.TextBoxBuilder().setColor("black").setHeight(100).setWidth(100).setX(50).setY(50).setOpacity(0.5).createTextBox();
		Caption caption1 = new Caption.CaptionBuilder(font, "First One").setColor("white").setSize(40).setX(50).setY(30).setMovingByY(true).setRepeatY(true).setMovingSpeed(7).setStartPositionOffset(100).setTextBox(textBox1).createCaption();
		VideoObject videoObject1 = new VideoObject.VideoObjectBuilder(input).setFadeIn(new FadeIn.FadeInBuilder(0, 1).createFadeIn()).setFadeOut(new FadeOut.FadeOutBuilder(1, 1).createFadeOut()).setCaption(caption1).createVideoObject();
		videoObjectList.add(videoObject1);

		//settings for video 2
		Caption caption2 = new Caption.CaptionBuilder(font, "Second One").setColor("white").setSize(40).setX(50).setY(30).setMovingByY(true).setMovingSpeed(7).setStartPositionOffset(100).createCaption();
		VideoObject videoObject2 = new VideoObject.VideoObjectBuilder(input).setFadeIn(new FadeIn.FadeInBuilder(0, 1).createFadeIn()).setFadeOut(new FadeOut.FadeOutBuilder(1, 1).createFadeOut()).setCaption(caption2).createVideoObject();
		videoObjectList.add(videoObject2);

		//set duration to each video
		ffprobe.setDuration(videoObjectList);

		//define stitching settings
		FFmpegBuilder builder = new FFmpegBuilder().overrideOutputFiles(true).addOutput(output).stitchVideos(videoObjectList).done();

		//execute
		FFmpegExecutor executor = new FFmpegExecutor(this.ffmpeg, this.ffprobe);
		FFmpegJob job = executor.createJob(builder);
		job.run();

		//add output video
		List<VideoObject> videoObjectOutputList = new ArrayList<>();
		VideoObject videoObject3 = new VideoObject.VideoObjectBuilder(output).createVideoObject();
		videoObjectOutputList.add(videoObject3);

		//set duration to output video
		ffprobe.setDuration(videoObjectOutputList);

		//check video length
		assertEquals(8.871, videoObjectList.get(0).getEnd(), 3);
		assertEquals(17.764, videoObjectOutputList.get(0).getEnd(), 1);
	}

	@Test
	public void testStitchVideosDifferentResolution() throws IOException {
		//initialize videoObjectsList
		String font = getClass().getResource("Roboto-Regular.ttf").getPath();
		String input1 = getClass().getResource("video.mp4").getPath();
		String input2 = getClass().getResource("video1.mp4").getPath();
		String output = input1.substring(0, input1.lastIndexOf(File.separator)) + File.separator + "output.mp4";
		List<VideoObject> videoObjectList = new ArrayList<>();

		//settings for video 1
		TextBox textBox1 = new TextBox.TextBoxBuilder().setColor("black").setHeight(100).setWidth(100).setX(50).setY(50).setOpacity(0.5).createTextBox();
		Caption caption1 = new Caption.CaptionBuilder(font, "~±!@#$%^&*()_-=+:;'/.?,<>\\…≥«≠–ºª•¶§¢£™¡§").setColor("white").setDisableNormalExp(true).setSize(40).setX(50).setY(30).setMovingByY(true).setRepeatY(true).setMovingSpeed(7).setStartPositionOffset(100).setTextBox(textBox1).createCaption();
		VideoObject videoObject1 = new VideoObject.VideoObjectBuilder(input1).setVolume(0.5).setFadeIn(new FadeIn.FadeInBuilder(0, 1).createFadeIn()).setFadeOut(new FadeOut.FadeOutBuilder(1, 1).createFadeOut()).setCaption(caption1).setResolutionX(1280).setResolutionY(720).createVideoObject();
		videoObjectList.add(videoObject1);

		//settings for video 2
		Caption caption2 = new Caption.CaptionBuilder(font, "Second One").setColor("white").setSize(40).setX(50).setY(30).setMovingByY(true).setMovingSpeed(7).setStartPositionOffset(100).createCaption();
		VideoObject videoObject2 = new VideoObject.VideoObjectBuilder(input2).setVolume(1.5).setFadeIn(new FadeIn.FadeInBuilder(0, 1).createFadeIn()).setFadeOut(new FadeOut.FadeOutBuilder(1, 1).createFadeOut()).setCaption(caption2).setResolutionX(1280).setResolutionY(720).createVideoObject();
		videoObjectList.add(videoObject2);

		//set duration to each video
		ffprobe.setDuration(videoObjectList);

		//define stitching settings
		FFmpegBuilder builder = new FFmpegBuilder().overrideOutputFiles(true).addOutput(output).stitchVideos(videoObjectList).done();

		//execute
		FFmpegExecutor executor = new FFmpegExecutor(this.ffmpeg, this.ffprobe);
		FFmpegJob job = executor.createJob(builder);
		job.run();

		//add output video
		List<VideoObject> videoObjectOutputList = new ArrayList<>();
		VideoObject videoObject3 = new VideoObject.VideoObjectBuilder(output).createVideoObject();
		videoObjectOutputList.add(videoObject3);

		//set duration to output video
		ffprobe.setDuration(videoObjectOutputList);

		//check video length
		assertEquals(13.886, videoObjectOutputList.get(0).getEnd(), 1);
	}

	@Test
	public void testStitchShorterVideos() throws IOException {
		//initialize videoObjectsList
		String input = getClass().getResource("video.mp4").getPath();
		String output = input.substring(0, input.lastIndexOf(File.separator)) + File.separator + "output.mp4";
		String font = getClass().getResource("Roboto-Regular.ttf").getPath();
		List<VideoObject> videoObjectList = new ArrayList<>();

		//settings for video 1
		TextBox textBox1 = new TextBox.TextBoxBuilder().setColor("black").setHeight(100).setWidth(100).setX(50).setY(50).setOpacity(0.5).createTextBox();
		Caption caption1 = new Caption.CaptionBuilder(font, "First One").setColor("white").setSize(40).setX(50).setY(30).setMovingByY(true).setRepeatY(true).setMovingSpeed(7).setStartPositionOffset(100).setTextBox(textBox1).createCaption();
		VideoObject videoObject1 = new VideoObject.VideoObjectBuilder(input).setFadeIn(new FadeIn.FadeInBuilder(0, 1).createFadeIn()).setFadeOut(new FadeOut.FadeOutBuilder(1, 1).createFadeOut()).setCaption(caption1).setStart(2).setEnd(4).createVideoObject();
		videoObjectList.add(videoObject1);

		//settings for video 2
		Caption caption2 = new Caption.CaptionBuilder(font, "Second One").setColor("white").setSize(40).setX(50).setY(30).setMovingByY(true).setMovingSpeed(7).setStartPositionOffset(100).createCaption();
		VideoObject videoObject2 = new VideoObject.VideoObjectBuilder(input).setFadeIn(new FadeIn.FadeInBuilder(0, 1).createFadeIn()).setFadeOut(new FadeOut.FadeOutBuilder(1, 1).createFadeOut()).setCaption(caption2).setStart(4).setEnd(6).createVideoObject();
		videoObjectList.add(videoObject2);

		//set duration to each video
		ffprobe.setDuration(videoObjectList);

		//define stitching settings
		FFmpegBuilder builder = new FFmpegBuilder().overrideOutputFiles(true).addOutput(output).stitchVideos(videoObjectList).done();

		//execute
		FFmpegExecutor executor = new FFmpegExecutor(this.ffmpeg, this.ffprobe);
		FFmpegJob job = executor.createJob(builder);
		job.run();

		//add output video
		List<VideoObject> videoObjectOutputList = new ArrayList<>();
		VideoObject videoObject3 = new VideoObject.VideoObjectBuilder(output).createVideoObject();
		videoObjectOutputList.add(videoObject3);

		//set duration to output video
		ffprobe.setDuration(videoObjectOutputList);

		//check video length
		assertEquals(4.024, videoObjectOutputList.get(0).getEnd(), 1);
	}

	@Test
	public void testImageOverlay() throws IOException {
		String font = getClass().getResource("Roboto-Regular.ttf").getPath();
		String input = getClass().getResource("video1.mp4").getPath();
		String overlayImage = getClass().getResource("gradient.png").getPath();
		String output = input.substring(0, input.lastIndexOf(File.separator)) + File.separator + "output.mp4";

		List<VideoObject> videoObjectList = new ArrayList<>();
		//settings for video 1
		List<Caption> captionList = new ArrayList<>();
		Caption title = new Caption.CaptionBuilder(font, "Darth Vader").setColor("white").setSize(30).setX(30).setY(230).createCaption();
		Caption description = new Caption.CaptionBuilder(font, "I am your father").setColor("white").setSize(20).setX(30).setY(260).setAlpha(0.5).createCaption();

		captionList.add(title);
		captionList.add(description);

		VideoObject videoObjectOverlay = new VideoObject.VideoObjectBuilder(overlayImage).setVideoStartOffset(0.5).setFadeIn(new FadeIn.FadeInBuilder(0, 1).createFadeIn()).setFadeOut(new FadeOut.FadeOutBuilder(2, 1).setLengthUnknown(true).createFadeOut()).setOverlayY(720 - 300).setCaptions(captionList).createVideoObject();
		VideoObject videoObject1 = new VideoObject.VideoObjectBuilder(input).setFadeIn(new FadeIn.FadeInBuilder(0, 1).createFadeIn()).setFadeOut(new FadeOut.FadeOutBuilder(1, 1).createFadeOut()).setOverlayVideo(videoObjectOverlay).createVideoObject();
		videoObjectList.add(videoObject1);

		//set duration to each video
		ffprobe.setDuration(videoObjectList);

		//define stitching settings
		FFmpegBuilder builder = new FFmpegBuilder().overrideOutputFiles(true).addOutput(output).stitchVideos(videoObjectList).done();

		//execute
		FFmpegExecutor executor = new FFmpegExecutor(this.ffmpeg, this.ffprobe);
		FFmpegJob job = executor.createJob(builder);
		job.run();

		//add output video
		List<VideoObject> videoObjectOutputList = new ArrayList<>();
		VideoObject videoObject3 = new VideoObject.VideoObjectBuilder(output).createVideoObject();
		videoObjectOutputList.add(videoObject3);

		//set duration to output video
		ffprobe.setDuration(videoObjectOutputList);

		//check video length
		assertEquals(5.02, videoObjectOutputList.get(0).getEnd(), 1);
	}

	@Test
	public void testMovieTrim1() throws IOException {
		String input = getClass().getResource("video1.mp4").getPath();
		String output = input.substring(0, input.lastIndexOf(File.separator)) + File.separator + "output.mp4";

		List<VideoObject> videoObjectList = new ArrayList<>();

		//settings for main video
		VideoObject mainVideo = new VideoObject.VideoObjectBuilder(input).setStart(1).setDuration(3).setFadeIn(new FadeIn.FadeInBuilder(0, 1).createFadeIn()).setFadeOut(new FadeOut.FadeOutBuilder(2, 1).setLengthUnknown(true).createFadeOut()).createVideoObject();
		videoObjectList.add(mainVideo);

		//set duration to each video
		ffprobe.setDuration(videoObjectList);

		//define stitching settings
		FFmpegBuilder builder = new FFmpegBuilder().overrideOutputFiles(true).addOutput(output).stitchVideos(videoObjectList).done();

		//execute
		FFmpegExecutor executor = new FFmpegExecutor(this.ffmpeg, this.ffprobe);
		FFmpegJob job = executor.createJob(builder);
		job.run();

		//add output video
		List<VideoObject> videoObjectOutputList = new ArrayList<>();
		VideoObject videoObject3 = new VideoObject.VideoObjectBuilder(output).createVideoObject();
		videoObjectOutputList.add(videoObject3);

		//set duration to output video
		ffprobe.setDuration(videoObjectOutputList);

		//check video length
		assertEquals(3.02, videoObjectOutputList.get(0).getEnd(), 1);
	}

	@Test
	public void testMovieTrim2() throws IOException {
		String input = getClass().getResource("video1.mp4").getPath();
		String output = input.substring(0, input.lastIndexOf(File.separator)) + File.separator + "output.mp4";

		List<VideoObject> videoObjectList = new ArrayList<>();

		//settings for main video
		VideoObject mainVideo = new VideoObject.VideoObjectBuilder(input).setStart(1).setEnd(4).setFadeIn(new FadeIn.FadeInBuilder(0, 1).createFadeIn()).setFadeOut(new FadeOut.FadeOutBuilder(2, 1).setLengthUnknown(true).createFadeOut()).createVideoObject();
		videoObjectList.add(mainVideo);

		//set duration to each video
		ffprobe.setDuration(videoObjectList);

		//define stitching settings
		FFmpegBuilder builder = new FFmpegBuilder().overrideOutputFiles(true).addOutput(output).stitchVideos(videoObjectList).done();

		//execute
		FFmpegExecutor executor = new FFmpegExecutor(this.ffmpeg, this.ffprobe);
		FFmpegJob job = executor.createJob(builder);
		job.run();

		//add output video
		List<VideoObject> videoObjectOutputList = new ArrayList<>();
		VideoObject videoObject3 = new VideoObject.VideoObjectBuilder(output).createVideoObject();
		videoObjectOutputList.add(videoObject3);

		//set duration to output video
		ffprobe.setDuration(videoObjectOutputList);

		//check video length
		assertEquals(3.02, videoObjectOutputList.get(0).getEnd(), 1);
	}

	@Test
	public void testMovieOverlay() throws IOException {
		String font = getClass().getResource("Roboto-Regular.ttf").getPath();
		String input = getClass().getResource("video1.mp4").getPath();
		String overlayImage = getClass().getResource("gradient.mov").getPath();
		String output = input.substring(0, input.lastIndexOf(File.separator)) + File.separator + "output.mp4";

		List<VideoObject> videoObjectList = new ArrayList<>();

		//caption for overlay Video
		List<Caption> captionList = new ArrayList<>();
		Caption title = new Caption.CaptionBuilder(font, "Darth Vader").setColor("white").setSize(30).setX(30).setY(230).createCaption(); //y coordinate should be set relative to overlay video height and not the height of the main video.
		Caption description = new Caption.CaptionBuilder(font, "I am your father").setColor("white").setSize(20).setX(30).setY(260).setAlpha(0.5).createCaption();
		captionList.add(title);
		captionList.add(description);

		//settings for overlay video
		//fade out effect should be shorter than duration of overlay video to work properly.
		VideoObject videoObjectOverlay = new VideoObject.VideoObjectBuilder(overlayImage).setVideoStartOffset(1).setFadeIn(new FadeIn.FadeInBuilder(0, 1).createFadeIn()).setFadeOut(new FadeOut.FadeOutBuilder(0.8, 1).setLengthUnknown(true).createFadeOut()).setOverlayY(720 - 300).setCaptions(captionList).createVideoObject();

		//settings for main video
		VideoObject mainVideo = new VideoObject.VideoObjectBuilder(input).setFadeIn(new FadeIn.FadeInBuilder(0, 1).createFadeIn()).setFadeOut(new FadeOut.FadeOutBuilder(1, 1).createFadeOut()).setOverlayVideo(videoObjectOverlay).createVideoObject();
		videoObjectList.add(mainVideo);

		//set duration to each video
		ffprobe.setDuration(videoObjectList);

		//define stitching settings
		FFmpegBuilder builder = new FFmpegBuilder().overrideOutputFiles(true).addOutput(output).stitchVideos(videoObjectList).done();

		//execute
		FFmpegExecutor executor = new FFmpegExecutor(this.ffmpeg, this.ffprobe);
		FFmpegJob job = executor.createJob(builder);
		job.run();

		//add output video
		List<VideoObject> videoObjectOutputList = new ArrayList<>();
		VideoObject videoObject3 = new VideoObject.VideoObjectBuilder(output).createVideoObject();
		videoObjectOutputList.add(videoObject3);

		//set duration to output video
		ffprobe.setDuration(videoObjectOutputList);

		//check video length
		assertEquals(5.02, videoObjectOutputList.get(0).getEnd(), 1);
	}

}