package com.sample;

import java.net.URI;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.sample.util.Utils;

import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.mediaconvert.MediaConvertClient;
import software.amazon.awssdk.services.mediaconvert.model.ContainerSettings;
import software.amazon.awssdk.services.mediaconvert.model.ContainerType;
import software.amazon.awssdk.services.mediaconvert.model.CreateJobRequest;
import software.amazon.awssdk.services.mediaconvert.model.H264AdaptiveQuantization;
import software.amazon.awssdk.services.mediaconvert.model.H264CodecLevel;
import software.amazon.awssdk.services.mediaconvert.model.H264CodecProfile;
import software.amazon.awssdk.services.mediaconvert.model.H264DynamicSubGop;
import software.amazon.awssdk.services.mediaconvert.model.H264EntropyEncoding;
import software.amazon.awssdk.services.mediaconvert.model.H264FieldEncoding;
import software.amazon.awssdk.services.mediaconvert.model.H264FlickerAdaptiveQuantization;
import software.amazon.awssdk.services.mediaconvert.model.H264FramerateControl;
import software.amazon.awssdk.services.mediaconvert.model.H264FramerateConversionAlgorithm;
import software.amazon.awssdk.services.mediaconvert.model.H264GopBReference;
import software.amazon.awssdk.services.mediaconvert.model.H264GopSizeUnits;
import software.amazon.awssdk.services.mediaconvert.model.H264InterlaceMode;
import software.amazon.awssdk.services.mediaconvert.model.H264ParControl;
import software.amazon.awssdk.services.mediaconvert.model.H264QualityTuningLevel;
import software.amazon.awssdk.services.mediaconvert.model.H264QvbrSettings;
import software.amazon.awssdk.services.mediaconvert.model.H264RateControlMode;
import software.amazon.awssdk.services.mediaconvert.model.H264RepeatPps;
import software.amazon.awssdk.services.mediaconvert.model.H264SceneChangeDetect;
import software.amazon.awssdk.services.mediaconvert.model.H264Settings;
import software.amazon.awssdk.services.mediaconvert.model.H264SlowPal;
import software.amazon.awssdk.services.mediaconvert.model.H264SpatialAdaptiveQuantization;
import software.amazon.awssdk.services.mediaconvert.model.H264Syntax;
import software.amazon.awssdk.services.mediaconvert.model.H264Telecine;
import software.amazon.awssdk.services.mediaconvert.model.H264TemporalAdaptiveQuantization;
import software.amazon.awssdk.services.mediaconvert.model.H264UnregisteredSeiTimecode;
import software.amazon.awssdk.services.mediaconvert.model.HlsCaptionLanguageSetting;
import software.amazon.awssdk.services.mediaconvert.model.HlsClientCache;
import software.amazon.awssdk.services.mediaconvert.model.HlsCodecSpecification;
import software.amazon.awssdk.services.mediaconvert.model.HlsDirectoryStructure;
import software.amazon.awssdk.services.mediaconvert.model.HlsGroupSettings;
import software.amazon.awssdk.services.mediaconvert.model.HlsManifestCompression;
import software.amazon.awssdk.services.mediaconvert.model.HlsManifestDurationFormat;
import software.amazon.awssdk.services.mediaconvert.model.HlsOutputSelection;
import software.amazon.awssdk.services.mediaconvert.model.HlsProgramDateTime;
import software.amazon.awssdk.services.mediaconvert.model.HlsSegmentControl;
import software.amazon.awssdk.services.mediaconvert.model.HlsStreamInfResolution;
import software.amazon.awssdk.services.mediaconvert.model.HlsTimedMetadataId3Frame;
import software.amazon.awssdk.services.mediaconvert.model.Input;
import software.amazon.awssdk.services.mediaconvert.model.JobSettings;
import software.amazon.awssdk.services.mediaconvert.model.M3u8NielsenId3;
import software.amazon.awssdk.services.mediaconvert.model.M3u8PcrControl;
import software.amazon.awssdk.services.mediaconvert.model.M3u8Scte35Source;
import software.amazon.awssdk.services.mediaconvert.model.M3u8Settings;
import software.amazon.awssdk.services.mediaconvert.model.Output;
import software.amazon.awssdk.services.mediaconvert.model.OutputGroup;
import software.amazon.awssdk.services.mediaconvert.model.OutputGroupSettings;
import software.amazon.awssdk.services.mediaconvert.model.OutputGroupType;
import software.amazon.awssdk.services.mediaconvert.model.TimedMetadata;
import software.amazon.awssdk.services.mediaconvert.model.VideoCodec;
import software.amazon.awssdk.services.mediaconvert.model.VideoCodecSettings;
import software.amazon.awssdk.services.mediaconvert.model.VideoDescription;

public class Create360PVideoImpl {

	public void createVideo(String format) {
		
		System.out.println("Started the 360P Encoding");

		OutputGroup outputGroup = null;
		
		if(Utils.isEmpty(format))
			format = "360p";
		
		int qvbrMaxBitrate = 3500000;
		int qvbrQualityLevel = 8;

		HashMap<String, String> userMetaData = new HashMap<String, String>();
		userMetaData.put("customer", "WIPRO");
		userMetaData.put("created", Instant.now().toString());


		MediaConvertClient mcClient = MediaConvertClient.builder()
				.credentialsProvider(EnvironmentVariableCredentialsProvider.create())
				.region(Region.US_EAST_2)
				.endpointOverride(URI.create("https://fkuulejsc.mediaconvert.us-east-2.amazonaws.com"))
				.build();

		Collection<Output> outputCollection = new ArrayList<Output>();

		Output output360p = Output.builder()
				.nameModifier("360P")
				.containerSettings(ContainerSettings.builder().container(ContainerType.M3_U8)
                        .m3u8Settings(M3u8Settings.builder().audioFramesPerPes(4)
                                .pcrControl(M3u8PcrControl.PCR_EVERY_PES_PACKET).pmtPid(480).privateMetadataPid(503)
                                .programNumber(1).patInterval(0).pmtInterval(0).scte35Source(M3u8Scte35Source.NONE)
                                .scte35Pid(500).nielsenId3(M3u8NielsenId3.NONE).timedMetadata(TimedMetadata.NONE)
                                .timedMetadataPid(502).videoPid(481)
                                .audioPids(482, 483, 484, 485, 486, 487, 488, 489, 490, 491, 492).build())
                        .build())
				.videoDescription(VideoDescription.builder()
						.width(640)
						.height(360)
						.codecSettings(VideoCodecSettings.builder()
								.codec(VideoCodec.H_264)
								.h264Settings(H264Settings.builder()
										.rateControlMode(H264RateControlMode.QVBR)
                                        .parControl(H264ParControl.INITIALIZE_FROM_SOURCE)
                                        .qualityTuningLevel(H264QualityTuningLevel.SINGLE_PASS)
                                        .qvbrSettings(H264QvbrSettings.builder()
                                                .qvbrQualityLevel(qvbrQualityLevel).build())
                                        .codecLevel(H264CodecLevel.AUTO)
                                        .codecProfile(H264CodecProfile.MAIN)
                                        .maxBitrate(qvbrMaxBitrate)
                                        .framerateControl(H264FramerateControl.INITIALIZE_FROM_SOURCE)
                                        .gopSize(90.0).gopSizeUnits(H264GopSizeUnits.FRAMES)
                                        .numberBFramesBetweenReferenceFrames(2).gopClosedCadence(1)
                                        .gopBReference(H264GopBReference.DISABLED)
                                        .slowPal(H264SlowPal.DISABLED).syntax(H264Syntax.DEFAULT)
                                        .numberReferenceFrames(3).dynamicSubGop(H264DynamicSubGop.STATIC)
                                        .fieldEncoding(H264FieldEncoding.PAFF)
                                        .sceneChangeDetect(H264SceneChangeDetect.ENABLED).minIInterval(0)
                                        .telecine(H264Telecine.NONE)
                                        .framerateConversionAlgorithm(
                                                H264FramerateConversionAlgorithm.DUPLICATE_DROP)
                                        .entropyEncoding(H264EntropyEncoding.CABAC).slices(1)
                                        .unregisteredSeiTimecode(H264UnregisteredSeiTimecode.DISABLED)
                                        .repeatPps(H264RepeatPps.DISABLED)
                                        .adaptiveQuantization(H264AdaptiveQuantization.HIGH)
                                        .spatialAdaptiveQuantization(
                                                H264SpatialAdaptiveQuantization.ENABLED)
                                        .temporalAdaptiveQuantization(
                                                H264TemporalAdaptiveQuantization.ENABLED)
                                        .flickerAdaptiveQuantization(
                                                H264FlickerAdaptiveQuantization.DISABLED)
                                        .softness(0).interlaceMode(H264InterlaceMode.PROGRESSIVE).build())
								.build())
						.build())
				.build();

		outputCollection.add(output360p);
		outputGroup = OutputGroup.builder()
				.name("APPLE HLS ABR 360P VIDEO ENCODING")
				.outputs(outputCollection)
				.outputGroupSettings(
						OutputGroupSettings.builder()
						.type(OutputGroupType.HLS_GROUP_SETTINGS)
						.hlsGroupSettings(HlsGroupSettings.builder()
								.destination(Constants.OUTPUT_S3_BUCKET_NAME)
								.directoryStructure(HlsDirectoryStructure.SINGLE_DIRECTORY)
                                .manifestDurationFormat(HlsManifestDurationFormat.INTEGER)
                                .streamInfResolution(HlsStreamInfResolution.INCLUDE)
                                .clientCache(HlsClientCache.ENABLED)
                                .captionLanguageSetting(HlsCaptionLanguageSetting.OMIT)
                                .manifestCompression(HlsManifestCompression.NONE)
                                .codecSpecification(HlsCodecSpecification.RFC_4281)
                                .outputSelection(HlsOutputSelection.MANIFESTS_AND_SEGMENTS)
                                .programDateTime(HlsProgramDateTime.EXCLUDE).programDateTimePeriod(600)
                                .timedMetadataId3Frame(HlsTimedMetadataId3Frame.PRIV).timedMetadataId3Period(10)
                                .segmentControl(HlsSegmentControl.SEGMENTED_FILES)
                                .minFinalSegmentLength((double) 0)
                                .segmentLength(10)
                                .minSegmentLength(0)
								.build())
						.build())
				.build();


		JobSettings jobSettings = JobSettings.builder()
				.inputs(Input.builder().fileInput(Constants.INPUT_S3_BUCKET_NAME_FOR_MEDIACONVERT+Constants.INPUT_VIDEO_FILE).build())
				.outputGroups(outputGroup)
				.build();		

		CreateJobRequest createJobRequest = CreateJobRequest.builder()
				.settings(jobSettings)
				.role(Constants.IAM_ROLE)
				.queue(Constants.QUEUE)
				.userMetadata(userMetaData)
				.build();

		mcClient.createJob(createJobRequest);
		System.out.println("Completed the 360P Encoding");
		
		
	}

	public static void main(String[] args) {
		
		Create360PVideoImpl createVideo = new Create360PVideoImpl();
		createVideo.createVideo("360p");
		
		
	}

}
