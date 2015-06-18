A demo android app project for pubnub push notification implementation.


NOTE: Provide your keys in keys.xml before running the project.


Sample pubnub json to be sent:

{
    "pn_gcm": {
        "data" : {
            "message": "hello world channel 1",
            "title":"test"
        }
    }
}