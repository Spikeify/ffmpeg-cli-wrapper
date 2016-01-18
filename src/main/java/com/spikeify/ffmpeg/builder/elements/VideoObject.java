package com.spikeify.ffmpeg.builder.elements;

import java.util.ArrayList;
import java.util.List;

public class VideoObject {

	private String path; //video path
	private double start = -1; //video start time
	private double end = -1; //video end time
	private double duration;
	private double videoStartOffset; //used for video overlays.

	private double overlayX = 0;
	private double overlayY = 0;

	private FadeIn fadeIn;
	private FadeOut fadeOut;

	private List<Caption> captions;
	private Caption caption;

	private List<VideoObject> overlayVideoList;

	private int resolutionX;
	private int resolutionY;

	private double volume;

	private VideoObject(String path, double start, double end, double duration, double videoStartOffset, double overlayX, double overlayY, FadeIn fadeIn, FadeOut fadeOut, List<Caption> captions, Caption caption, List<VideoObject> overlayVideoList, int resolutionX, int resolutionY, double volume) {
		this.path = path;
		this.start = start;
		this.end = end;
		this.duration = duration;
		this.videoStartOffset = videoStartOffset;
		this.overlayX = overlayX;
		this.overlayY = overlayY;
		this.fadeIn = fadeIn;
		this.fadeOut = fadeOut;
		this.captions = captions;
		this.caption = caption;
		this.overlayVideoList = overlayVideoList;
		this.resolutionX = resolutionX;
		this.resolutionY = resolutionY;
		this.volume = volume;
	}

	public double getVolume() {
		return volume;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public double getStart() {
		return start;
	}

	public void setStart(double start) {
		this.start = start;
	}

	public double getEnd() {
		return end;
	}

	public void setEnd(double end) {
		this.end = end;
	}

	public FadeIn getFadeIn() {
		return fadeIn;
	}

	public void setFadeIn(FadeIn fadeInBuilder) {
		this.fadeIn = fadeInBuilder;
	}

	public FadeOut getFadeOut() {
		return fadeOut;
	}

	public void setFadeOut(FadeOut fadeOut) {
		this.fadeOut = fadeOut;
	}

	public List<Caption> getCaptions() {
		return captions;
	}

	public void setCaptions(List<Caption> captions) {
		this.captions = captions;
	}

	public Caption getCaption() {
		return caption;
	}

	public void setCaption(Caption caption) {
		this.caption = caption;
	}

	public double getOverlayX() {
		return overlayX;
	}

	public double getOverlayY() {
		return overlayY;
	}

	public double getDuration() {
		return duration;
	}

	public List<VideoObject> getOverlayVideoList() {
		return overlayVideoList;
	}

	public double getVideoStartOffset() {
		return videoStartOffset;
	}

	public int getResolutionX() {
		return resolutionX;
	}

	public int getResolutionY() {
		return resolutionY;
	}

	public static class VideoObjectBuilder {
		private String path;
		private double start = -1;
		private double end = -1;
		private double duration = -1;
		private double overlayX;
		private double overlayY;
		private FadeIn fadeIn;
		private FadeOut fadeOut;
		private List<Caption> captions;
		private Caption caption;
		private List<VideoObject> overlayVideoList;
		private double videoStartOffset; //used for video overlays.
		private int resolutionX;
		private int resolutionY;
		private double volume;

		public VideoObjectBuilder(String path) {
			this.path = path;
		}

		public VideoObjectBuilder setStart(double start) {
			this.start = start;
			return this;
		}

		public VideoObjectBuilder setEnd(double end) {
			this.end = end;
			return this;
		}

		public VideoObjectBuilder setFadeIn(FadeIn fadeIn) {
			this.fadeIn = fadeIn;
			return this;
		}

		public VideoObjectBuilder setFadeOut(FadeOut fadeOut) {
			this.fadeOut = fadeOut;
			return this;
		}

		public VideoObjectBuilder setCaptions(List<Caption> captions) {
			this.captions = captions;
			return this;
		}

		public VideoObjectBuilder setCaption(Caption caption) {
			this.caption = caption;
			return this;
		}

		public VideoObjectBuilder setOverlayVideo(VideoObject overlayVideo) {
			if(overlayVideo != null){
				List<VideoObject> videoObjectList = new ArrayList<>();
				videoObjectList.add(overlayVideo);
				this.overlayVideoList = videoObjectList;
			}
			return this;
		}

		public VideoObjectBuilder setOverlayX(double overlayX) {
			this.overlayX = overlayX;
			return this;
		}

		public VideoObjectBuilder setOverlayY(double overlayY) {
			this.overlayY = overlayY;
			return this;
		}

		public VideoObjectBuilder setDuration(double duration) {
			this.duration = duration;
			return this;
		}

		/* this operation is not supported yet */
//		public VideoObjectBuilder setOverlayVideoList(List<VideoObject> overlayVideoList) {
//			this.overlayVideoList = overlayVideoList;
//			return this;
//		}

		public VideoObjectBuilder setVideoStartOffset(double videoStartOffset) {
			this.videoStartOffset = videoStartOffset;
			return this;
		}

		public VideoObjectBuilder setResolutionX(int resolutionX) {
			this.resolutionX = resolutionX;
			return this;
		}

		public VideoObjectBuilder setResolutionY(int resolutionY) {
			this.resolutionY = resolutionY;
			return this;
		}

		public VideoObjectBuilder setVolume(double volume) {
			this.volume = volume;
			return this;
		}

		public VideoObject createVideoObject() {
			return new VideoObject(path, start, end, duration, videoStartOffset, overlayX, overlayY, fadeIn, fadeOut, captions, caption, overlayVideoList, resolutionX, resolutionY, volume);
		}


	}
}
