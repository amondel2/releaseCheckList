window.rootNodeId = 'root';
var intialPersonDropDowns = 5;
var dropdownlist = ['portfolio','projects','subproj','jobs','jobSubFunction'];
var workDropdownCount = 0;
var currentsmonth = 0;
var currenendmonth = 11;

var cantEdit = [];

$(function() {
    initialStyling();
    setFormSubmitHandler();
    setFormInputHandler();
     createJSTree();
     $("#myDate_year").on('change',function(){
         $("#YearChangeFrm").submit();
     })
});

$(document).on('click', function() {
    var formInput = $('.fm-form-input');

    removeHeaderMsg();
    formInput.removeClass('fm-error');
    formInput.siblings('.fm-validation-msg').text('');
});

function initialStyling() {
    $('input[type="button"], input[type="submit"]').button();
}

function createJSTree() {
    var jstree = $('#jstree');

    var plugins = ['wholerow', 'sort', 'dnd']

    jstree.jstree({
        plugins: plugins,
        core: {
            check_callback: function(operation, node, parentNode, nodePosition, more) {
                if (more && more.dnd && operation === 'move_node') {
                    return isDroppable(parentNode, more);
                }

                return true;
            },
            strings: {
                'Loading ...': window.fmLoadingMsg
            },
            multiple: false,
            force_text: true,
            themes: {
                icons: false
            },
            data: function(obj, callback) {
                if (this._cnt === 0) {
                    getInitialData(this, callback);
                } else {
                    getPortfolioChildren(this, obj, callback);
                }
            }
        },
        sort: function(firstNodeId, secondNodeId) {
            var firstNode = this.get_node(firstNodeId).text;
            var secondNode = this.get_node(secondNodeId).text;

            var status = firstNode.localeCompare(secondNode, undefined, {
                numeric: true,
                sensitivity: 'base'
            });

            return status >= 0 ? 1 : -1;
        },
        dnd: {
            large_drop_target: true,
            large_drag_target: true,
            touch: true,
            is_draggable: function(data) {
                if (data[0].id === 'root') {
                    return false;
                }

                return true;
            }
        }
    });

    jstree.on('select_node.jstree', function(e, data) {
        var portfolioData = data.node.data;
        setJSTreeSelectionLogic(portfolioData);
    });

    jstree.on('move_node.jstree', handleDragAndDrop);

    jstree.on('loaded.jstree refresh.jstree', function() {
        toggleExportButtonVisibility();
        $('#' + window.rootNodeId + ' > div:first-child').click();
    });
}

function getInitialData(that, callback) {
    $.get(window.fmBaseDir + 'getInitialData',{year:workingYear,bossId:fmBossId}).then(function(data) {
        var startingTree = getInitialTree(data);
        $.each(data.childData,function(index,val) {
           if(!val.canedit) {
               cantEdit.push(val.id)
           }
        });
        callback.call(that, startingTree);
    }).fail(displayErrorMsg);
}

function getInitialTree(data) {
    return [
        {
            id: window.rootNodeId,
            text: window.fmCompanyName,
            state: {
                opened: true,
                selected: true
            },
            children: data.childData,
            data: {
                id: window.rootNodeId
            }
        }
    ];
}

function getPortfolioChildren(that, obj, callback) {
    $.get(window.fmBaseDir + 'loadEmployeeChildren', {
        id: obj.id,
        year: workingYear
    }).then(function(data) {
        callback.call(that, data);
    }).fail(displayErrorMsg);
}

function setJSTreeSelectionLogic(portfolioData) {
    if (portfolioData.id === window.rootNodeId) {
        $('.fm-field').text('');
        $('.fm-only-on-select').hide();
        $('.fm-right-header > div:not(:nth-child(3))').hide();
    } else {
        setHeaderInfo(portfolioData);
        $('.fm-right-header > div').css('display', 'flex');
        $('.fm-only-on-select').show();
    }

    $('.fm-right-form').hide();
    removeSelectedBtnClassFromAll()
}

function setHeaderInfo(data) {
    var name = data.name,
        employeeId = data.employeeId;

    $('#fm-name-field').text(name);
    $('#fm-portfolio-id-field').text(employeeId);
}

function setFormInfo() {
    var name = $('#fm-name-field').text(),
        employeeId = $('#fm-portfolio-id-field').text();

    $('#fm-name-input').val(name);
    $('#fm-employeeId-input').val(employeeId);
}

function createChildBtnHandler(el) {
    setSelectedBtn(el);
    $('.fm-right-form input[type="text"]').val('');
    $('#fm-portfolioUnitId-form-field').show();
    $('.fm-right-form').css('display', 'flex');
}

function updateBtnHandler(el) {
    setSelectedBtn(el);
    setFormInfo();
    $('#fm-portfolioUnitId-form-field').hide();
    $('.fm-right-form').css('display', 'flex');
}

function deleteBtnHandler() {
    removeSelectedBtnClassFromAll();
    $('.fm-right-form').hide();

    $('#fm-delete-confirm').dialog({
        modal: true,
        width: 550,
        resizable: false,
        buttons: [
            {
                text: window.fmConfirmMsg,
                click: function() {
                    deletePortfolio();
                    $(this).dialog('close');
                }
            },
            {
                text: window.fmCancelMsg,
                click: function() {
                    $(this).dialog('close');
                }
            }
        ]
    });
}


function copyEditDropDowns(disableDropDowns,startmonth,endmonth){
    var jstree = $('#jstree').jstree(true);
    var currentNodeId = jstree.get_selected();
    var tr = $("<tr></tr>");
    $.each($("#dropdowns").children(),function(index,val){
        var item = $(val).clone(true,true);
        if(disableDropDowns) {
            $(item).prop('disabled',true);
    }
        var td = $("<td></td>");
        $(item).attr('name',workDropdownCount + "_" + $(item).attr('name') + currentNodeId);
        $(item).appendTo(td);
        $(td).appendTo(tr);
    });
    $.each($("#tableItems").children(),function(index,val){
        var item = $(val).clone(true,true);
        var td = $("<td></td>");
        $(item).attr('name',workDropdownCount + "_" + $(item).attr('name') + "_" + currentNodeId);
        if(index < startmonth || index  > endmonth ) {
            $(item).prop('disabled',true);
        }
        $(item).appendTo(td);
        $(td).appendTo(tr);
    });
    var td = $("<td></td>");
    var item = $("<button name='delete' onclick='deleteRow("+workDropdownCount + ",\""+currentNodeId+"\");'>Delete</button>");
    $(item).appendTo(td);
    $(td).appendTo(tr);
    $(tr).appendTo('#fm-editpersonwork table');
}

function populateDropDown(counter,workDropdownCount,currentNodeId,resultSet) {
    if(counter >= dropdownlist.length) {
        return true;
    } else {
        var value = dropdownlist[counter];
        if( resultSet[value] ) {
            $('select[name="' + workDropdownCount + '_' + value + '_' + currentNodeId + '"]').find('option:selected').removeAttr('selected');
            $('select[name="' + workDropdownCount + '_' + value + '_' + currentNodeId + '"]').find('option[value="' + resultSet[value] + '"]').attr("selected", true);
            $.when($('select[name="' + workDropdownCount + '_' + value + '_' + currentNodeId + '"]').trigger('change')).then(function () {
                populateDropDown(++counter, workDropdownCount, currentNodeId, resultSet);
            });
        } else {
            populateDropDown(++counter, workDropdownCount, currentNodeId, resultSet);
        }
    }
}

function markLeave() {
    var jstree = $('#jstree').jstree(true);
    var currentNodeId = jstree.get_selected();
    if(cantEdit.indexOf(currentNodeId[0]) >= 0 ) {
        alert("NO ACCESS TO EDIT THIS PERSON");
        return;
    }
    removeSelectedBtnClassFromAll();
    $('.fm-right-form').hide();

    $('#fm-markleave').dialog({
        modal: true,
        minWidth: 800,
        minHeight: 250,
        maxHeight: 600,
        resizable: true,
        open: function( event, ui ) {
            var jstree = $('#jstree').jstree(true);
            var currentNodeId = jstree.get_selected();
            $("#leaveEmpId").val(currentNodeId[0]);
            $('#fm-markleave').dialog( "option", "title", "Mark Exit for " + $.trim(jstree.get_selected(true)[0]['text']));
        },
        buttons: [
            {
                text: 'close',
                click: function() {
                    $(this).dialog('close');
                }
            },
            {
                text: 'save',
                click: function () {
                        var data = {};
                        var that = this;
                        data['leaveReason'] = $("#leaveReason").val();
                        data['leaveDate'] = $("#leaveDate_month").val() + "/" + $("#leaveDate_day").val() + "/" + $("#leaveDate_year").val();
                        data['empId'] =$("#leaveEmpId").val();
                        $.post(window.fmBaseDir + 'saveLeaveDate', data)
                            .then(function(res) {
                                if(res.rs == "Success") {
                                    displaySuccessMsg()
                                }
                                $(that).dialog('close');
                            });
                }
            }
    ]
    });
}

function editpersonwork() {
    var jstree = $('#jstree').jstree(true);
    var currentNodeId = jstree.get_selected();
    if(cantEdit.indexOf(currentNodeId[0]) >= 0 ) {
        alert("NO ACCESS TO EDIT THIS PERSON");
        return;
    }
    removeSelectedBtnClassFromAll();
    $('.fm-right-form').hide();

    $('#fm-editpersonwork').dialog({
        modal: true,
        minWidth: 800,
        minHeight: 250,
        maxHeight: 600,
        resizable: true,

        open: function( event, ui ) {
            var jstree = $('#jstree').jstree(true);
            var currentNodeId = jstree.get_selected();
            workDropdownCount = 0;
            $('#editpersonresponsetable tr td').find('select').remove();
            $('#editpersonresponsetable tr td').find('input').remove();
            $('#editpersonresponsetable tr:not(:first)').remove();

            var data = {};
            data['empId'] = currentNodeId[0];
            data['year'] = workingYear;
            $.post(window.fmBaseDir + 'getResultSet', data)
                .then(function(res) {
                    currentsmonth = res.smonth;
                    currenendmonth = res.emonth;
                    if(res.rs && res.rs.length > 0 ) {
                        var x = 0;
                        for(x;x < res.rs.length;x++){
                            workDropdownCount++;
                            copyEditDropDowns(true,currentsmonth,currenendmonth);
                            var resultSet = res.rs[x].jobinfo;
                            populateDropDown(0,workDropdownCount,currentNodeId,resultSet);
                            var valueSet = res.rs[x].monthPercent;
                            var n=1;
                            var trinputs =   $($("#editpersonresponsetable tbody tr").get(workDropdownCount)).find('td input');
                            for(n;n <= 12;n++) {
                                $(trinputs[n-1]).val(valueSet[n]);
                            }
                        }
                    } else {
                        workDropdownCount = 1;
                        copyEditDropDowns(false,currentsmonth,currenendmonth);
                    }
                }).fail(displayErrorMsg);
            $('#fm-editpersonwork').dialog( "option", "title", $.trim(jstree.get_selected(true)[0]['text']));
        },
        buttons: [
            {
                text: 'close',
                click: function() {
                    $(this).dialog('close');
                }
            },
            {
                text: 'add',
                click: function() {
                    workDropdownCount++;
                    copyEditDropDowns(false,currentsmonth,currenendmonth);
                }
            },
            {
                text: 'save',
                click: function(){
                    var data = {};
                    var selectBoxes = {};
                    var hashBoxes = {};
                    var responses = {};
                    var i = 1;
                    var that = this;
                    var warn = false;
                    var valid = true;
                    var warnMsg = "";
                    var errorMsg = "";
                    var countPercentBox = [];
                    $("#fm-editpersonwork table tr td").css('backgroundColor','white');
                    for(i;i<=workDropdownCount;i++) {
                        var selectBox = {};
                        var hashBox = "";
                        $.each($("#editpersonresponsetable tr td select[name^='" + i + "_']"), function (index, val) {
                            if ($(val).find("option:selected").text() == 'Select') {
                                valid = false;
                                var elm = $(val).parent().parent();
                                errorMsg +=  "Row " +  $("#editpersonresponsetable tbody tr").index(elm)  + " is not completly filled out\n\r";
                                $(elm).find('td').css('backgroundColor','red');
                                return false;
                            } else {
                                hashBox += $(val).find("option:selected").text()
                            }

                            selectBox[$(val).attr('name')] = $(val).val();
                        });
                        if( hashBoxes[hashBox] == 1) {
                            valid = false;
                            errorMsg += "Multiple Rows of the same selection exists\r\n";
                        } else {
                            hashBoxes[hashBox] = 1;
                        }

                        selectBoxes[i] = selectBox;
                        var responseBox = {};
                        $.each($("#fm-editpersonwork table tr td input[name^='" + i + "_']"), function (index, td) {
                            responseBox[$(td).attr('name')] = $(td).val();
                            countPercentBox[index] = parseFloat(countPercentBox[index] ? countPercentBox[index] : 0)  + parseFloat($(td).val() ? $(td).val() : 0);
                        });
                        responses[i] = responseBox;
                    }
                    i=0;

                    for(i;i<12;i++) {
                        if(countPercentBox[i] > 100 ) {
                            valid = false;
                            var count = $("#fm-editpersonwork table tr").last().find('td select').length;
                            errorMsg += "Column " + (i + count + 1) + " is over 100\r\n";
                            $.each($("#fm-editpersonwork table tr td input").parent().parent(),function(index, val){

                                $($(val).find('td input').get(i)).parent().css('backgroundColor','red');
                            });
                        } else if (countPercentBox[i] > 0 && countPercentBox[i] < 100  ) {
                            warn = true;
                            var count = $("#fm-editpersonwork table tr").last().find('td select').length;
                            warnMsg += "Column " + (i + count + 1) + " is under 100\r\n";
                        }
                    }


                    var selLen = 0;
                    var skeys = Object.keys(selectBoxes);
                    var keyLen = skeys.length
                    var counter=0;
                    while(selLen == 0 && counter < keyLen) {
                        selLen = Object.keys(selectBoxes[skeys[counter++]]).length
                    }

                    if(selLen == 0) {
                        valid = false;
                        errorMsg = "You have not saved anything!"
                    }

                    if(!valid) {
                       alert(errorMsg);
                        return false;
                    } else if (warn) {
                        alert("WARNING:\r\n" + warnMsg);
                    }

                    data['successBoxes'] = JSON.stringify(selectBoxes);
                    data['responses'] = JSON.stringify(responses);
                    data['year'] = workingYear;

                    $.post(window.fmBaseDir + 'savePortfolioProjectJobs', data)
                        .then(function(res) {
                            var hasNoErrors = $.isEmptyObject(res.errors);
                            if (hasNoErrors) {
                                displaySuccessMsg();
                                $(that).dialog('close');
                            } else {
                                throwValidationErrors(res.errors);
                            }
                        })
                        .fail(displayErrorMsg);
                }
            }
        ]
    });
}

function setSelectedBtn(el) {
    removeSelectedBtnClassFromAll();
    $(el).addClass('fm-btn-selected');
}

function removeSelectedBtnClassFromAll() {
    $('.fm-right-info-buttonset input[type="button"]').removeClass('fm-btn-selected');
}

function setFormSubmitHandler() {
    $('form.fm-right-form').submit(function(e) {
        e.preventDefault();
        $('#fm-submit-btn').button('disable');

        var data = getFormData(this);
        var isCreateChild = $('#create-child-btn').hasClass('fm-btn-selected');
        if (isCreateChild) {
            createEmployee(data);
        } else {
            updatePortfolio(data);
        }
    });
}

function getFormData(form) {
    return $(form).serializeArray().reduce(function(obj, item) {
        obj[item.name] = item.value;
        return obj;
    }, {});
}

function createEmployee(data) {
    var currentlySelectedNode = $('#jstree').jstree(true).get_selected(true)[0];
    data.parentId = currentlySelectedNode.id !== window.rootNodeId ? currentlySelectedNode.id : null;

    $.post(window.fmBaseDir + 'createEmployee', data)
        .then(function(res) {
            var hasNoErrors = $.isEmptyObject(res.errors);
            if (hasNoErrors) {
                createNewEmployeeoInJSTree(res.saveData);
                displaySuccessMsg();
                toggleExportButtonVisibility();
            } else {
                throwValidationErrors(res.errors);
            }
        })
        .fail(displayErrorMsg)
        .done(enableFormSubmitBtn);
}

function createNewEmployeeoInJSTree(saveData) {
    var jstree = $('#jstree').jstree(true);
    var parentId = saveData.data.parentId != null ? saveData.data.parentId : window.rootNodeId;
    var parentIsLoaded = jstree.is_loaded(parentId);

    jstree.deselect_all();

    if (true) {
        var node = {
            id: saveData.id,
            text: saveData.text,
            state: {
                selected: true,
            },
            data: saveData.data,
            children: saveData.hasChildren
        };

        jstree.create_node(parentId, node, '', function() {
            jstree.open_node(parentId, setJSTreeSelectionLogic(saveData.data));
        });
    } else {
        jstree.load_node(parentId, function() {
            jstree.select_node(saveData.id);
        });
    }
}

function updatePortfolio(data) {
    var jstree = $('#jstree').jstree(true);
    var currentlySelectedNode = jstree.get_selected(true)[0];
    data.id = currentlySelectedNode.id;

    $.post(window.fmBaseDir + 'updatePortfolio', data)
        .then(function(res) {
            var hasNoErrors = $.isEmptyObject(res.errors);
            if (hasNoErrors) {
                updatePortfolioInJSTree(res.saveData);
                displaySuccessMsg();
                setJSTreeSelectionLogic(res.saveData.data);
            } else {
                throwValidationErrors(res.errors);
            }
        })
        .fail(displayErrorMsg)
        .done(enableFormSubmitBtn);
}

function updatePortfolioInJSTree(portfolioData) {
    var jstree = $('#jstree').jstree(true);
    var portfolio = jstree.get_node(portfolioData.id);
    jstree.rename_node(portfolioData.id, portfolioData.text);
    portfolio.data = portfolioData.data;
}

function throwValidationErrors(errorsObj) {
    Object.keys(errorsObj).forEach(function(key) {
        $('#fm-' + key + '-input').addClass('fm-error');
        $('#fm-' + key + '-validation-msg').text(errorsObj[key]);
    });
    displayValidationMsg();
}

function setFormInputHandler() {
    $(document).on('input', '.fm-form-input', function() {
        $('.fm-message .fm-error-msg').remove();
        $(this).removeClass('fm-error');
        $(this).siblings('.fm-validation-msg').text('');
    })
}

function displaySuccessMsg() {
    removeHeaderMsg();
    var msg = $('<div>').addClass('fm-success-msg').text(window.fmSuccessMsg);
    $('.fm-message').append(msg);
}

function displayValidationMsg() {
    removeHeaderMsg();
    var msg = $('<div>').addClass('fm-error-msg').text(window.fmErrorMsg);
    $('.fm-message').append(msg);
}

function displayErrorMsg() {
    removeHeaderMsg();
    var msg = $('<div>').addClass('fm-error-msg').text(window.fmProblemMsg);
    $('.fm-message').append(msg);
}

function removeHeaderMsg() {
    $('.fm-message .fm-success-msg, .fm-message .fm-error-msg').remove();
}

function deletePortfolio() {
    var jstree = $('#jstree').jstree(true);
    var currentNodeId = jstree.get_selected();
    var parentNodeId = jstree.get_parent(currentNodeId);

    var data = {
        id: currentNodeId[0]
    };

    $.post(window.fmBaseDir + 'deletePortfolio', data)
        .then(function() {
            jstree.delete_node(currentNodeId);
            jstree.select_node(parentNodeId);
            toggleExportButtonVisibility();
        })
        .fail(displayErrorMsg);
}

function getDropDownOptions(value,name,obj){


    var indexOfVal = dropdownlist.indexOf(name);
    if(indexOfVal < 0) {
        return false;
    }
    var data = {
        val: value,
        name: name,
        year: workingYear
    };
    // var parentId = $("#editpersonresponsetable").parent().attr("id");
    //delete everything after value
    var i = indexOfVal + 1;
    if(i < intialPersonDropDowns) {
        for (i; i < intialPersonDropDowns; i++) {
            var dname = dropdownlist[i];
            $(obj).parent().parent().find("td select[name*='" + dname + "']").find('option').remove().end().append('<option value="" disabled selected>Select</option>').val('');
        }
        //get the list of new values
        var ops = {
            type: "POST",
            url: window.fmBaseDir + 'getNextItems',
            data: data,
            async: false
        };
        $.post(ops).then(function (res) {
            var dname = dropdownlist[indexOfVal + 1];
            var selectbox = $(obj).parent().parent().find("td select[name*='" + dname + "']");
            $(selectbox).find('option').remove();
            var opts = res.opts;
            if(name == 'projects') {
                var selectbox23 = $(obj).parent().parent().find("td select[name*='subproj']");
                $(selectbox23).find('option').remove()
                if(res.opts.subproj.length > 0){
                    $(selectbox23).append($("<option></option>").attr("value", "").text("Select"));
                    for (var x = 0; x < res.opts.subproj.length; x++) {
                        $(selectbox23).append($("<option></option>").attr("value", res.opts.subproj[x].key).text(res.opts.subproj[x].value));
                    }
                } else {
                    $(selectbox23).append($("<option></option>").attr("value", "").text("None"));
                }
                dname = dropdownlist[++indexOfVal + 1];
                selectbox = $(obj).parent().parent().find("td select[name*='" + dname + "']");
                $(selectbox).find('option').remove();
                opts = res.opts.jobs;
            }
            if(opts.length > 0) {
                $(selectbox).append($("<option></option>").attr("value", "").text("Select"));
                for (var x = 0; x < opts.length; x++) {
                    $(selectbox).append($("<option></option>").attr("value", opts[x].key).text(opts[x].value));
                }
            } else {
                $(selectbox).append($("<option></option>").attr("value", "").text("None"));
                for (var x = indexOfVal + 2; x < dropdownlist.length; x++) {
                    var dname = dropdownlist[x];
                    $(obj).parent().parent().find("td select[name*='" + dname + "']").find('option').remove().end().append('<option value="" disabled selected>None</option>').val('');
                }
            }
            if( (res.opts && ( res.opts.smonth || res.opts.emonth) ) || ( (opts && opts.length > 0  ) && ( opts[0].smonth || opts[0].emonth ))) {
                var emonth
                var smonth
                try{
                    smonth = parseInt(res.opts.smonth);
                } catch( e) {
                    smonth = -1;
                }
                try{
                    emonth = parseInt(res.opts.emonth);
                } catch( e) {
                    emonth = 14;
                }
               $.each($(obj).parent().parent().find("td input"),function (index,val) {
                  if(index < smonth) {
                      $(val).prop('disabled',true);
                      $(val).val('');
                  } else if ( index > emonth ) {
                      $(val).prop('disabled',true);
                      $(val).val('');
                  } else {
                      $(val).prop('disabled',false);
                  }
               });
            }
        }).fail(displayErrorMsg);
    }

}

function createRows(){

}


function getCurrentWorkItems(){

    var jstree = $('#jstree').jstree(true);
    var currentNodeId = jstree.get_selected();
    var parentNodeId = jstree.get_parent(currentNodeId);
    var data = {
        id: currentNodeId[0]
    };

    $.post(window.fmBaseDir + 'currentWork', data)
        .then(function(res) {

        })
        .fail(displayErrorMsg);

}

function enableFormSubmitBtn() {
    $('#fm-submit-btn').button('enable');
}

function handleDragAndDrop(e, data) {
    var jstree = $('#jstree').jstree(true);
    var parentNodeId = data.parent;

    jstree.open_node(parentNodeId);

    var postData = {
        id: data.node.id,
        parentId: parentNodeId === 'root' ? null : parentNodeId
    };
    $.post(window.fmBaseDir + 'updateParentOfEmployee', postData)
        .fail(function() {
            displayErrorMsg();
            jstree.refresh();
        });
}

function isDroppable(parentNode, more) {
    var isOutOfTree = parentNode.id === '#';
    var isTryingToReorder = more.pos !== 'i';

    if (isOutOfTree || isTryingToReorder) {
        return false;
    }

    return true;
}

function toggleExportButtonVisibility() {
    var jstree = $('#jstree').jstree(true);
    var rootNode = jstree.get_node(window.rootNodeId);

    if (rootNode.children && rootNode.children.length > 0) {
        $('#exportPortfolios').show();
    } else {
        $('#exportPortfolios').hide();
    }
}

function deleteRow(index,empId) {
    var elm = $("select[name='"+index+"_portfolio_"+empId+"']").parent().parent();

    $("#empRowId").html($("#editpersonresponsetable tbody tr").index(elm));
    $( function() {
        $( "#dialog-confirm" ).dialog({
            resizable: false,
            height: "auto",
            width: 400,
            modal: true,
            buttons: {
                "Delete Row": function() {

                    var that = this;
                    var data= {};
                    var selectBox = {};
                    $.each($(elm).find('select'), function (index, val) {
                        selectBox[$(val).attr('name')] = $(val).val();
                    });

                    data['selectBox'] = JSON.stringify(selectBox);
                    data['empId'] = empId;
                    data['index'] = index;
                    $.post(window.fmBaseDir + 'deleteWorkItem', data)
                        .then(function(res) {
                            if(res.status == "success") {
                                $(elm).empty();
                                $(elm).remove();
                            }
                            $(that).dialog("close");
                        }).done(function(){
                            $(that).dialog("close");
0                    });
                },
                Cancel: function() {
                    $( this ).dialog( "close" );
                }
            }
        });
    } );
}