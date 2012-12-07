(function () {

    //切換頁籤重新整理 CodeMirror
    $('a[data-toggle="tab"]').on('shown', function (e) {
        $.each(editors, function(index, val) {
            editors[index].refresh();
        });
    });

    //generate params
    var __ajax_save_record_url = $('input[name="__ajax_save_record_url"]').val();
    var __ajax_client_port = $('input[name="__ajax_client_port"]').val();
    var __ajax_loader_image_src = $('input[name="__ajax_loader_image_src"]').val();

    //儲存記錄
    var fnSaveRecord = function(params, fncb) {
        $('#ajaxmsg').html('<img src="'+__ajax_loader_image_src+'" alt="ajaxloader" />');
        $.ajax({
            type: 'post',
            url: __ajax_save_record_url,
            data: params,
            success: function(data) {
                if ($.isFunction(fncb)) {
                    fncb();
                }
                $('#ajaxmsg').html(data);
            }
        });
    };

    //互動按鈕
    var fnCheckAnswer = function(ans) {
        var std = $('#answer').val();
        return trim(std)==trim(ans);
    };

    //顯示結果報表
    var fnShowResult = function(msg) {
        $('#modalmsg').html(msg);
        $('#myModal').modal();
    };

    if (editors && editors['sourceEdit']) {
        var editor = editors['sourceEdit'];

        $('#cmdPlay').click(function() {
            $.ajax({
                type: 'post',
                url: 'http://localhost:'+__ajax_client_port+'/',
                data: {
                    action: 'play',
                    sourceType: $('#sourceType').val(),
                    sourcePath: $('#sourcePath').val(),
                    sourceCode: editor.getValue()
                },
                timeout: 180*1000,
                success: function(data) {

                    if (data) {
                        $('#program-output').text(data.result.data.dump);

                        var ans = data.result.data.dump;
                        var std = $('#answer').val();

                        var report = $('<div class="test-report" />');

                        var lines = std.split("\n");
                        var lines2 = ans.split("\n");
                        var linec = 0;
                        var linec2 = 0;
                        for (i=0; i<lines.length; i++) {
                            linec++;
                            if (i < lines2.length) {
                                if (rtrim(lines[i])==rtrim(lines2[i])) {
                                    linec2++;
                                    report.append('<pre class="test-ok"><i class="icon icon-ok"></i> '+lines[i]+'</pre>');
                                }
                                else {
                                    report.append('<pre class="test-error"><i class="icon icon-remove"></i> '+lines2[i]+'</pre>');
                                    report.append('<pre class="test-hint"><i class="icon icon-ok"></i> '+lines[i]+'</pre>');
                                }
                            }
                        }

                        var passed = (linec == linec2);

                        report.append('<h4>測試結果</h4>');

                        if (passed) {
                            report.append('<font color="green"><i class="icon icon-ok"></i> 通過測試，請繼續做下一個題目</font>');
                        }
                        else {
                            report.append('<font color="red"><i class="icon icon-error"></i> 無法通過測試，請修正程式碼再重新執行一次</font>');
                        }

                        //report.append('<pre>'+ans+'</pre>');
                        //report.append('<h4>標準執行結果</h4>');
                        //report.append('<pre>'+std+'</pre>');

                        fnShowResult(report);

                        fnSaveRecord({
                            passed: passed,
                            answer: ans,
                            sourceCode: editor.getValue()
                        });
                    }
                },
                error: function(data) {
                    fnSaveRecord({
                        passed: false,
                        sourceCode: editor.getValue()
                    });
                    fnShowResult('<font color="red">錯誤！請先啟動客戶端工具。</font>');
                } 
            });
            return false;
        });

        $('#cmdSave').click(function() {
            $('#cmdProgress').show();
            fnSaveRecord({
                sourceCode: editor.getValue()
            }, function() {
                $('#cmdProgress').hide();
            });
            return false;
        });

        $('#cmdReset').click(function() {
            editor.setValue($('#partialCode').val());
            return false;
        });
        $('#cmdUndo').click(function() {
            editor.undo();
            return false;
        });
        $('#cmdRedo').click(function() {
            editor.redo();
            return false;
        });
    }

    if (editors && editors['sourceCode']) {
        var editor = editors['sourceCode'];

        //執行測試（教材編輯模式）
        $('#cmdDump').click(function() {
            $.ajax({
                type: 'post',
                url: 'http://localhost:'+__ajax_client_port+'/',
                data: {
                    action: 'play',
                    sourceType: $('#sourceType').val(),
                    sourcePath: $('#sourcePath').val(),
                    sourceCode: editor.getValue()
                },
                timeout: 180*1000,
                success: function(data) {
                    //alert("程式執行完畢");

                    if (data) {
                        editors['answer'].setValue(data.result.data.dump);
                    }
                },
                error: function(data) {
                    bootbox.alert("錯誤！請先啟動客戶端工具。");
                }
            });
            return false;
        });
    }

    $('#cmdCheck').change(function() {
        fnSaveRecord({passed: $(this).prop('checked')});
    });

    $('#cmdSubmit').click(function() {
        var ans = $('#myanswer').val();
        var passed = fnCheckAnswer(ans);
        fnSaveRecord({
            passed: passed,
            answer: ans
        });
        if (passed) {
            fnShowResult('<font color="blue">恭喜你答對了！</font>');
        }
        else {
            fnShowResult('<font color="red">答錯了，請再試一次！</font>');
        }
    });

    $('#cmdResizeFont').toggle(function() {
        $('.justfont').addClass('larger-font');
    }, function() {
        $('.justfont').removeClass('larger-font');
    });
})();