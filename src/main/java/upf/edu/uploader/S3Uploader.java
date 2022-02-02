package upf.edu.uploader;

import java.io.File;
import java.util.List;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.CreateBucketRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;

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

		// We create a bucket and we recover the client S3 connection
		final AmazonS3 s3 = createBucket(this.bucketName, this.profile);

		for (int i = 0; i < files.size(); i++) {

			// While we have files we upload

			s3.putObject(this.bucketName, prefix + new File(files.get(i)).getName(), (new File(files.get(i))));
			i++;
			System.out.println("File uploaded");
		}

	}

	public AmazonS3 createBucket(String bucketName, String profile) {
		/* Create S3 Client Object */
		final AmazonS3 s3 = AmazonS3ClientBuilder.standard().withCredentials(new ProfileCredentialsProvider(profile))
				.withRegion(Regions.US_EAST_1).build();
		Bucket b = null;
		// If the bucket exists we don't create a new one, we get the already existing one
		if (s3.doesBucketExistV2(bucketName)) {
			System.out.format("Bucket %s already exists.\n", bucketName);
			b = getBucket(bucketName, profile);

		} else {
			try {
				// We create a new bucket
				b = s3.createBucket(bucketName);
			} catch (AmazonS3Exception e) {
				System.err.println(e.getErrorMessage());
			}
		}
		return s3;
	}

	public static Bucket getBucket(String bucket_name, String profile) {
		// We login into the s3 client
		final AmazonS3 s3 = AmazonS3ClientBuilder.standard().withCredentials(new ProfileCredentialsProvider(profile))
				.withRegion(Regions.US_EAST_1).build();
		Bucket named_bucket = null;
		List<Bucket> buckets = s3.listBuckets();
		// We check every bucket in our S3 client and we compare them with our bucket name
		for (Bucket b : buckets) {
			if (b.getName().equals(bucket_name)) {
				named_bucket = b;
			}
		}
		return named_bucket;
	}

}
