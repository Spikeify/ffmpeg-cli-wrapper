package com.spikeify.ffmpeg.builder;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.spikeify.ffmpeg.probe.FFmpegProbeResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Builds a ffmpeg command line
 *
 * @author bramp
 */
public class FFmpegBuilder {

	final static Logger LOG = LoggerFactory.getLogger(FFmpegBuilder.class);

	public enum Strict {
		VERY,        // strictly conform to a older more strict version of the spec or reference software
		STRICT,      // strictly conform to all the things in the spec no matter what consequences
		NORMAL,
		UNOFFICAL,   // allow unofficial extensions
		EXPERIMENTAL;

		//ffmpeg command line requires these options in the lower case
		public String toString() {
			return name().toLowerCase();
		}
	}

	// Global Settings
	public boolean override = true;
	public int pass = 0;
	public String pass_prefix;
	public boolean enable_logging;

	// Input settings
	public Long startOffset; // in millis
	public String input;
	public FFmpegProbeResult inputProbe;
	public int video_num_thumbnails = 0;
	public int thumbnailQuality = 1;

	// Output
	public List<FFmpegOutputBuilder> outputs = new ArrayList<FFmpegOutputBuilder>();

	//sticher
	public boolean isSticher;
	public ImmutableList.Builder<String> sticherArgs;


	public FFmpegBuilder overrideOutputFiles(boolean override) {
		this.override = override;
		return this;
	}

	/**
	 * create tumbnails from a video
	 *
	 * @param numThumbnails - number of tumbnails to output
	 * @return FFmpegOutputBuilder
	 */
	public FFmpegBuilder setVideoTumbnails(int numThumbnails) {
		this.video_num_thumbnails = numThumbnails;
		return this;
	}

	/**
	 * Set thumbnail quality
	 * @param quality Quality (1-32, 1 is best)
	 * @return
	 */
	public FFmpegBuilder setThumbnailQuality(int quality) {
		this.thumbnailQuality = quality;
		return this;
	}

	public boolean getOverrideOutputFiles() {
		return this.override;
	}

	public FFmpegBuilder setPass(int pass) {
		this.pass = pass;
		return this;
	}

	public FFmpegBuilder setPassPrefix(String prefix) {
		this.pass_prefix = prefix;
		return this;
	}

	public FFmpegBuilder setInput(String filename) {
		this.input = filename;
		return this;
	}

	public FFmpegBuilder setInput(FFmpegProbeResult result) {
		this.inputProbe = checkNotNull(result);
		this.input = checkNotNull(result.format).filename;

		return this;
	}

	public FFmpegBuilder setStartOffset(long duration, TimeUnit units) {
		checkNotNull(duration);
		checkNotNull(units);

		this.startOffset = units.toMillis(duration);

		return this;
	}

	/**
	 * enable or disable logging with multiple passes.
	 */
	public FFmpegBuilder enableMultipassLogging(boolean enableLogging) {
		enable_logging = enableLogging;
		return this;

	}

	/**
	 * Create new output file
	 *
	 * @param filename
	 * @return A new FFmpegOutputBuilder
	 */
	public FFmpegOutputBuilder addOutput(String filename) {
		FFmpegOutputBuilder output = new FFmpegOutputBuilder(this, filename);
		outputs.add(output);
		return output;
	}

	/**
	 * Create new output (to stdout)
	 */
	public FFmpegOutputBuilder addStreamedOutput() {
		return addOutput("-");
	}

	public List<String> build() {
		ImmutableList.Builder<String> args;
		if (isSticher) {
			args = sticherArgs;

		} else {
			args = new ImmutableList.Builder<String>();
			Preconditions.checkArgument(input != null, "Input must be specified");
			args.add("-v", "error"); // TODO make configurable

			if (startOffset != null) {
				args.add("-ss").add(String.format("%.3f", startOffset / 1000f));
			}

			args.add("-i").add(input);

			if (pass > 0) {
				args.add("-pass").add(Integer.toString(pass));

				if (pass_prefix != null && enable_logging) {
					args.add("-passlogfile").add(pass_prefix);
				}
			}

			if (video_num_thumbnails > 0) {
				args.add("-vframes").add(String.format("%d", video_num_thumbnails));
				args.add("-q:v").add(String.format("%d", thumbnailQuality));
			}
		}
		args.add(override ? "-y" : "-n");
		Preconditions.checkArgument(!outputs.isEmpty(), "At least one output must be specified");
		for (FFmpegOutputBuilder output : this.outputs) {
			args.addAll(output.build(pass));
		}


		return args.build();
	}
}
