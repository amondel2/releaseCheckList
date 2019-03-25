function finishSection() {
    var isFinished = true;
    $.each($("#currContainer div input[type=checkbox]"),function (index,value) {
        if(isFinished) {
            isFinished = $(value).prop('checked');
        }
    });
    if(!isFinished) { return false;}
    $.ajax( {
        url: baseDir + "saveSection",
        dataType: 'json',
        type: 'post',
        cache: false,
        data: {id:$("#currContainer div ul").attr('sectionid')}
    }).done(function(data) { completeSection();});

}

var completeSection = function() {
    $("#prevContainer div").html($("#currContainer div").html());
    $("#prevContainer div input").prop('checked',true);
    $("#prevContainer div input").prop('disabled',true);
    $("#prevContainer div input").prop('readOnly',true);

    if($("#nextContainer div").children().find('input').length > 0) {
        getNextItem(true);
        getNextItem('false');
    } else {
        completeRelease();
    }

};

function completeRelease(){
    $.ajax( {
        url: baseDir + "completeRelease",
        dataType: 'json',
        type: 'post',
        cache: false,
        data: {id:$("#releaseId").attr('relId')}
    }) .done(function(data) {
        if(data.status == "SUCCESS") {
            window.location.href = baseDir + "index"

        } else {

        }
    }).fail(function() {
        alert( "error" );
    });
}


function getNextItem(forCurrent){

    $.ajax( {
        url: baseDir + "getNextItem",
        dataType: 'json',
        type: 'post',
        cache: false,
        data: {forCurrent:forCurrent,id:$("#releaseId").attr('relId')}
    })
        .done(function(data) {
            var mydiv = forCurrent;
            if(data.status == "SUCCESS") {
                $.ajax({
                    url:  baseDir + "getReleaseItems",
                    type: "post",
                    dataType: 'text',
                    cache: false,
                    data: data.obj
                }).done(function (mydata) {
                    if (mydiv === true) {
                        $("#currContainer div").html(mydata);
                    } else if(mydiv === 'prev') {
                        $("#prevContainer div").html(mydata);
                        $("#prevContainer div input").prop('checked',true);
                        $("#prevContainer div input").prop('disabled',true);
                        $("#prevContainer div input").prop('readOnly',true);
                    } else {
                        $("#nextContainer div").html(mydata);
                        $("#nextContainer div input").prop('disabled',true);
                        $("#nextContainer div input").prop('readOnly',true);
                    }
                });
            } else {
                if (mydiv === true) {
                    $("#currContainer div").html("");
                } else if(mydiv === 'prev') {
                    $("#prevContainer div").html("");
                } else {
                    $("#nextContainer div").html("");
                }
            }
        })
        .fail(function() {
            alert( "error" );
        });
}

$(document).ready(function(){
    getNextItem(true);
    getNextItem('false');
    getNextItem('prev');
    $("#currContainer div").on("change","input",function() {
        $.ajax( {
            url: baseDir + "saveItem",
            dataType: 'json',
            type: 'post',
            cache: false,
            async: false,
            data: {isChecked:$(this).prop('checked') ? 'true' : 'false',id:$(this).attr('id')}
        }).done(finishSection());
    });
});
