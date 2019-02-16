# FFmpeg Java
[ffmpeg-cli-wrapper](https://github.com/bramp/ffmpeg-cli-wrapper) by Andrew Brampton 2013-2014, upgraded dependencies and added additional features by Dean Gostiša and Roman Orač in 2015.

## Added features
We added support for (check examples in unit tests):

 - additional transcoding parameters,
 - creating thumbnails from input video,
 - getting a dominant color of created thumbnail (with [Color Thief](https://github.com/SvenWoltmann/color-thief-java)),
 - trim videos,
 - stitching multiple videos with fade in, fade out effects, text, text box and video overlays. 

## Requirements

Install static build ffmpeg to use all added features.

## Setup

    <dependency>
        <groupId>com.spikeify</groupId>
        <artifactId>ffmpeg</artifactId>
        <version>check for latest version</version>
    </dependency>

## Usage

### Init FFmpeg

    FFmpeg ffmpeg = new FFmpeg("/path/to/ffmpeg");
	FFprobe ffprobe = new FFprobe("/path/to/ffprobe");

### Create thumbnails

    FFmpegBuilder builder = new FFmpegBuilder().setInput("inputVideo")
                    .setVideoTumbnails(10) //number of frames to extract
                    .addOutput("thumbnail-%03d.jpg")
                    .done();

    FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);
    FFmpegJob job = executor.createJob(builder);
    job.run();

### Get dominant color

    File img = new File("image.png");
    BufferedImage image = ImageIO.read(img);
    String dominantHex = ColorThief.getDominantHex(image, 1, true);
    
### Stitching multiple videos

    List<VideoObject> videoObjectList = new ArrayList<>();
    
    //settings for video 1
    Caption caption1 = new Caption.CaptionBuilder("someFont.ttf", "Text").setColor("white").setSize(40).setX(50).setY(50).createCaption();
    VideoObject videoObject1 = new VideoObject.VideoObjectBuilder("video1.mp4").setFadeIn(new FadeIn.FadeInBuilder(0, 1).createFadeIn()).setFadeOut(new FadeOut.FadeOutBuilder(1, 1).createFadeOut()).setCaption(caption1).createVideoObject();
    videoObjectList.add(videoObject1);

    //settings for video 2
    VideoObject videoObject2 = new VideoObject.VideoObjectBuilder("video2.mp4").setFadeIn(new FadeIn.FadeInBuilder(0, 1).createFadeIn()).setFadeOut(new FadeOut.FadeOutBuilder(1, 1).createFadeOut()).createVideoObject();
    videoObjectList.add(videoObject2);

    ffprobe.setDuration(videoObjectList); //set duration to each video

    FFmpegBuilder builder = new FFmpegBuilder().overrideOutputFiles(true).addOutput("output.mp4").stitchVideos(videoObjectList).done();
    FFmpegExecutor executor = new FFmpegExecutor(this.ffmpeg, this.ffprobe);
    FFmpegJob job = executor.createJob(builder);
    job.run();
