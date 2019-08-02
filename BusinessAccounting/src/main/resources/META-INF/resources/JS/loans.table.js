var $table1 = $('#table1');
var $lookRepayPlan = $('#lookRepayPlan');
var $lookRepayFlow = $('#lookRepayFlow');
var $dqhk = $('#dqhk');
var $tqjqCur = $('#tqjqCur');
var $tqjqAll = $('#tqjqAll');

function initTable1() {
    $table1.bootstrapTable({
    	url: "../loan/listLoans",
    	reinit: false,
    	classes: "table table-hover",
    	pagination: true,
    	toolbar: "#toolbar",
    	search: true,
    	striped: true,//是否显示行间隔色
    	clickToSelect: true,//是否启用点击选中行
    	responseHandler: function (res) {
            return res;
        },
        columns:
            [	
            	{
            		radio: true,
            	}, {
                    field: 'id',
                    title: '编号',
                    visiable: 'false'
                }, {
                    field: 'term',
                    title: '期数',
                    align: 'center'
                }, {
                    field: 'loanPrincipal',
                    title: '贷款本金',
                    align: 'center'
                }, {
                    field: 'repayMethod',
                    title: '还款方式',
                    align: 'center',
                    formatter:function(value,row,index){
                    	if(value == "debx"){
                    		return "等额本息";
                    	}else if(value == "debj"){
                    		return "等额本金";
                    	}else if(value == "xxhb"){
                    		return "先息后本";
                    	}else if(value == "one"){
                    		return "一次性还本付息";
                    	}else {
                    		return value;
                    	}
                    }
                }, {
                    field: 'loanDate',
                    title: '放款日期',
                    align: 'center'
                }, {
                    field: 'endDate',
                    title: '截止日期',
                    align: 'center'
                }, {
                    field: 'finishDate',
                    title: '结清日期',
                    align: 'center'
                }, {
                    field: 'dayInterestRate',
                    title: '日利率(%)',
                    align: 'center'
                }, {
                    field: 'loanStatus',
                    title: '借据状态',
                    align: 'center',
                    formatter:function(value,row,index){
                    	if(value == "zc"){
                    		return "正常";
                    	}else if(value == "yq"){
                    		return "逾期";
                    	}else if(value == "zcjq"){
                    		return "正常结清";
                    	}else if(value == "tqjq"){
                    		return "提前结清";
                    	}else if(value == "yqjq"){
                    		return "逾期结清";
                    	}else {
                    		return value;
                    	}
                    }
                }
            ]
    });
}


function getIdSelections() {
    return $.map($table1.bootstrapTable('getSelections'), function (row) {
        return row.id
    });
}

function addButton2Event(){
	$lookRepayPlan.click(function () {
	    var loanId = getIdSelections();
	    if(loanId==""){
	    	layer.msg("至少选择一条", {icon: 5});
	    	return;
	    }
	    var opt = {
            url: "../loan/repayscheduleAction?loanId="+loanId,
            silent: false //静默刷新
        };
	    $table2.bootstrapTable('refresh', opt);
	});
}

function addButton3Event(){
	$lookRepayFlow.click(function () {
	    var loanId = getIdSelections();
	    if(loanId==""){
	    	layer.msg("至少选择一条", {icon: 5});
	    	return;
	    }
	    var opt = {
            url: "../loan/repayflowAction?loanId="+loanId,
            silent: false //静默刷新
        };
	    $table3.bootstrapTable('refresh', opt);
	});
}

function addButtonEvent_dqhk(){
	$dqhk.click(function () {
	    var loanId = getIdSelections();
	    if(loanId==""){
	    	layer.msg("至少选择一条", {icon: 5});
	    	return;
	    }
	    $.ajax({
		   type: "POST",
		   url: "../repay/repayAction",
		   data:"loanId="+loanId+"&repaymode=dqhk",
		   success: function(msg){
			   layer.msg(msg.tip, {icon:5,time:1500});
		   }
 		});
	});
}

function addButtonEvent_tqjqCur(){
	$tqjqCur.click(function () {
	    var loanId = getIdSelections();
	    if(loanId==""){
	    	layer.msg("至少选择一条", {icon: 5});
	    	return;
	    }
	    $.ajax({
		   type: "POST",
		   url: "../repay/repayAction",
		   data:"loanId="+loanId+"&repaymode=tqjqCur",
		   success: function(msg){
			   layer.msg(msg.tip, {icon:5,time:1500});
		   }
 		});
	});
}

function addButtonEvent_tqjqAll(){
	$tqjqAll.click(function () {
	    var loanId = getIdSelections();
	    if(loanId==""){
	    	layer.msg("至少选择一条", {icon: 5});
	    	return;
	    }
	    $.ajax({
		   type: "POST",
		   url: "../repay/repayAction",
		   data:"loanId="+loanId+"&repaymode=tqjqAll",
		   success: function(msg){
			   layer.msg(msg.tip, {icon:5,time:1500});
		   }
 		});
	});
}

$(function() {
	initTable1();
	initTable2();
	initTable3();
	addButton2Event();
	addButton3Event();
	
	addButtonEvent_dqhk();
	addButtonEvent_tqjqCur();
	addButtonEvent_tqjqAll();
});