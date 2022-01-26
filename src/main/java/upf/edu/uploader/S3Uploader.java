package upf.edu.uploader;

import java.util.List;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

public class S3Uploader implements Uploader {
	private final String bucketName;
	private final String prefix;
	private final String profile;

	public S3Uploader(String bucketName, String prefix, String profile) {
		this.bucketName = bucketName;
		this.prefix = prefix;
		this.profile = profile;

	}

	@Override
	public void upload(List<String> files) {
		// TODO Auto-generated method stub
		AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withRegion(Regions.US_WEST_2)
                .build();
		Region region = Region.US_WEST_2;
        S3Client s3 = S3Client.builder().region(region).build();

        String bucket = "new-bucket12345";
        String key = "key";

        // Put Object
        s3.putObject(PutObjectRequest.builder().bucket(bucket).key(key)
                .build(), RequestBody.fromByteBuffer(getRandomByteBuffer(10000)));

	}

}
