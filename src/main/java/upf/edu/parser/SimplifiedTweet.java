package upf.edu.parser;

import com.google.gson.Gson;
//import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.openjdk.nashorn.internal.parser.JSONParser;

import java.io.Serializable;
import java.util.Optional;

public class SimplifiedTweet {

	private final long tweetId; // the id of the tweet ('id')
	private final String text; // the content of the tweet ('text')
	private final long userId; // the user id ('user->id')
	private final String userName; // the user name ('user'->'name')
	private final String language; // the language of a tweet ('lang')
	private final long timestampMs; // seconduserIds from epoch ('timestamp_ms')

	public SimplifiedTweet(long tweetId, String text, long userId, String userName, String language, long timestampMs) {
		// PLACE YOUR CODE HERE!
		this.tweetId = tweetId;
		this.text = text;
		this.userId = userId;
		this.userName = userName;
		this.language = language;
		this.timestampMs = timestampMs;
	}

	/**
	 * Returns a {@link SimplifiedTweet} from a JSON String. If parsing fails, for
	 * any reason, return an {@link Optional#empty()}
	 *
	 * @param jsonStr
	 * @return an {@link Optional} of a {@link SimplifiedTweet}
	 */
	public static Optional<SimplifiedTweet> fromJson(String jsonStr) {

		// PLACE YOUR CODE HERE!

		try {
			JsonObject jo = JsonParser.parseString(jsonStr).getAsJsonObject();
			String name = "";
			Long id = (long) 0;
			if (jo.has("user")) {
				JsonObject userObj = jo.getAsJsonObject("user");
				name = jo.getAsJsonObject("user").get("name").getAsString();
				id = jo.getAsJsonObject("user").get("id").getAsLong();

			}
			SimplifiedTweet tweet = new SimplifiedTweet(jo.get("id").getAsLong(), jo.get("text").getAsString(), id,
					name, jo.get("lang").getAsString(), jo.get("timestamp_ms").getAsLong());
			Optional<SimplifiedTweet> optionalTweet = Optional.of(tweet);
			return optionalTweet;

		} catch (Exception ex) {
			System.out.println("Null found");
			return Optional.empty();
		}

	}

	@Override
	public String toString() {
		return new Gson().toJson(this);

	}

	public String getLanguage() {
		return language;
	}
}
