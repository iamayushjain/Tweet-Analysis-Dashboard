var stompClient = null;
var source=null;
var text_string = '';
var sentimentData=[
    {label:"STRONG_NEGATIVE", color:"tomato", value:0},
    {label:"WEAK_NEGATIVE", color:"darksalmon", value:0},
    {label:"NEUTRAL", color:"yellow", value:0},
    {label:"WEAK_POSITIVE", color:"yellowgreen", value:0},
    {label:"STRONG_POSITIVE", color:"darkseagreen", value:0}
    ];

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
        sendName();
    }
    else {
        $("#conversation").hide();
    }
    $("#feedBlock").html("");
}

function connect() {
    var socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe("/topic/source."+source, function (liveTweets) {
            console.log(liveTweets);
            var response = JSON.parse(liveTweets.body);
            for (var k in response){
                showTweet(response[k].text, response[k].sentiment);
            }
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    stompClient.send("/app/live/"+source, {}, {})
}

function showTweet(message, sentiment) {
    $("#feedBlock").prepend("<tr><td> <div class = '"+getClassFromSentiment(sentiment)+"'>" + message + "</div></td></tr>");
    text_string+=message;
    updateSentimentData(sentiment);
    generateWordCloud();
//    generateDonut3D();
}
function updateSentimentData(sentiment){
    for(var k in sentimentData){
        if(sentimentData[k].label == sentiment)
            sentimentData[k].value++;
    }
}

function getClassFromSentiment(sentiment){
    switch(sentiment){
        case "STRONG_NEGATIVE":
            return "strong_negative";
        case "WEAK_NEGATIVE":
            return "weak_negative";
        case "NEUTRAL":
            return "neutral";
        case "WEAK_POSITIVE":
            return "weak_positive";
        case "STRONG_POSITIVE":
            return "strong_positive";
        default: return "";
    }
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { initConnection(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendName(); });
    initConnection();
});

function initConnection(){
    source = getUrlParam("source", null);
    if(source!=null){
        connect();
    }else{
        alert("Please Input Source");
    }
}

function getUrlParam(parameter, defaultvalue){
    var urlparameter = defaultvalue;
    if(window.location.href.indexOf(parameter) > -1){
        urlparameter = getUrlVars()[parameter];
    }
    return urlparameter;
}
function getUrlVars() {
    var vars = {};
    var parts = window.location.href.replace(/[?&]+([^=&]+)=([^&]*)/gi, function(m,key,value) {
        vars[key] = value;
    });
    return vars;
}
