package com.rarasnet.rnp.centros.controllers.network;

import com.rarasnet.rnp.centros.controllers.network.requests.SearchCentersRequest;
import com.rarasnet.rnp.centros.controllers.network.responses.SearchCentersDataResponse;
import com.rarasnet.rnp.shared.network.connections.RarasConnection;

/**
 * Created by Ronnyery Barbosa on 04/02/2016.
 */
public class SugegetionCentersAdapter {

    //private String searchURL = "http://192.168.56.2/webservice/rest_profissionais.json";

    public SearchCentersDataResponse[] searchCenter(String userInput, String searchOption) throws Exception {
        //byte[] authEncBytes = Base64.encode(userInput.getBytes(), 0);
        //String encond = new String(authEncBytes);
        // byte[] bytes = userInput.getData();

        String searchInput = userInput;
        //Log.d("a5", params[0]);
        String searchType = searchOption;

        SearchCentersRequest searchCentersRequest = new SearchCentersRequest(userInput, searchOption);

        SearchCentersDataResponse[] searchCentersDataResponses = null;

        RarasConnection requester = new RarasConnection(RarasConnection.ACTION_SEARCH_CENTERS);

        requester.sendRequest(searchCentersRequest, SearchCentersRequest.class);

        searchCentersDataResponses = requester.getResponse(SearchCentersDataResponse[].class);


        return searchCentersDataResponses;
    }



        /*String teste = "çasá"
                ;        String latin1Result = new String(userInput.getBytes("ISO-8859-1"), "ISO-8859-1");
        Log.d("input input teste  json", latin1Result);
        //a userInput = "Doen\u00e7a de Alexander";

        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("searchInput", userInput));
        params.add(new BasicNameValuePair("searchOption", searchOption));

        Log.d("input option json", searchOption);
        Log.d("input input json",userInput);
        DefaultHttpClient httpClient = new DefaultHttpClient();


        try {

            HttpPost httpPost = new HttpPost(searchURL);

            httpPost.setEntity(new UrlEncodedFormEntity(params));
            Log.d("input input json", httpPost.toString());

            HttpResponse httpResponse = httpClient.execute(httpPost);

            HttpEntity entity = httpResponse.getEntity();

            if (entity != null) {
                InputStream instream = entity.getContent();
                String json = toString(instream);
                instream.close();

                Log.d("JSON RESPONSE:", json);

                List<Professional> disorders = getDiseases(json);
                return disorders;
            }
        } catch (Exception e) {
            throw e;
        }
        return null;
    }

    private List<Professional> getDiseases(String jsonString) throws Exception {
        List<Professional> disorders = new ArrayList<Professional>();
        Gson gson = new Gson();



        try {
            JSONObject ob = new JSONObject(jsonString);
            JSONArray jArray = ob.getJSONArray("profissionai");

            int i = 0;
            while (!jArray.isNull(i)) {
                String stringDisorder = jArray.getString(i);



                Log.d("utf",stringDisorder);

                Professional dis = gson.fromJson(stringDisorder, Professional.class);

                disorders.add(dis);
                i++;
            }
        } catch (JSONException e) {
            Log.d("ERRO:", e.getMessage());
            throw new Exception();
        }
        return disorders;
    }

    private String toString(InputStream is) throws IOException {
        byte[] bytes = new byte[1024];
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int lidos;
        while ((lidos = is.read(bytes)) > 0) {
            baos.write(bytes, 0, lidos);
        }
        return new String(baos.toByteArray());
    }*/
}
