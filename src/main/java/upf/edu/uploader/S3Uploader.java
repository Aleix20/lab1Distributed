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
		final AmazonS3 s3 = createBucket(this.bucketName, this.profile);

		while (!files.isEmpty()) {
			int i = 0;
			
			s3.putObject(this.bucketName, new File(files.get(i)).getName(), prefix + (new File(files.get(i))).getName());
			i++;
		}

	}

	public AmazonS3 createBucket(String bucketName, String profile) {
		/* Create S3 Client Object */

		final AmazonS3 s3 = AmazonS3ClientBuilder.standard().withCredentials(new ProfileCredentialsProvider(profile))
				.withRegion(Regions.US_EAST_1).build();
		Bucket b = null;
		if (s3.doesBucketExistV2(bucketName)) {
			System.out.format("Bucket %s already exists.\n", bucketName);
			b = getBucket(bucketName, profile);

		} else {
			try {
				b = s3.createBucket(bucketName);
			} catch (AmazonS3Exception e) {
				System.err.println(e.getErrorMessage());
			}
		}
		return s3;
	}
	public static Bucket getBucket(String bucket_name, String profile) {
        final AmazonS3 s3 =AmazonS3ClientBuilder.standard().withCredentials(new ProfileCredentialsProvider(profile))
				.withRegion(Regions.US_EAST_1).build();
        Bucket named_bucket = null;
        List<Bucket> buckets = s3.listBuckets();
        for (Bucket b : buckets) {
            if (b.getName().equals(bucket_name)) {
                named_bucket = b;
            }
        }
        return named_bucket;
    }

}
