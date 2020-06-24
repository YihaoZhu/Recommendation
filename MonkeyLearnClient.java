package external;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.monkeylearn.ExtraParam;
import com.monkeylearn.MonkeyLearn;
import com.monkeylearn.MonkeyLearnException;
import com.monkeylearn.MonkeyLearnResponse;

public class MonkeyLearnClient {

	public static final String API_KEY = "2cbf257c37ee5c9f6d3912b834702187ff983e7e";

	public static void main(String[] args) throws MonkeyLearnException {

		String[] data = {
				"Elon Musk has shared a photo of the spacesuit designed by SpaceX. This is the second image shared of the new design and the first to feature the spacesuit’s full-body look." };
		
		List<List<String>> words = extractKeywords(data);
		for(List<String> ws: words) {
			for(String w: ws) {
				System.out.println(w);
			}
			System.out.println();
		}
	}

	public static List<List<String>> extractKeywords(String[] text) {
		if (text == null || text.length == 0) {
			return new ArrayList<>();
		}
		MonkeyLearn ml = new MonkeyLearn(API_KEY);

		String modelId = "ex_YCya9nrn";

		ExtraParam[] extraParams = { new ExtraParam("max_keywords", "3") };

		MonkeyLearnResponse response;
		try {
			response = ml.extractors.extract(modelId, text, extraParams);
			JSONArray resultArray = response.arrayResult;
			return getKeywords(resultArray);

		} catch (MonkeyLearnException e) {// it’s likely to have an exception
			e.printStackTrace();
		}
		
		return null;
	}

	private static List<List<String>> getKeywords(JSONArray resultArray) {
		List<List<String>> topKeywords = new ArrayList<>();
		// i represents 20 job desciptions
		for (int i = 0; i < resultArray.size(); i++) {

			List<String> keywords = new ArrayList<>();

			JSONArray keywordsArray = (JSONArray) resultArray.get(i);
			// every job description has j keywords
			for (int j = 0; j < keywordsArray.size(); j++) {
				JSONObject object = (JSONObject) keywordsArray.get(j);
				String keyword = (String) object.get("keyword");
				keywords.add(keyword);
			}
			topKeywords.add(keywords);
		}
		return topKeywords;
	}
}
