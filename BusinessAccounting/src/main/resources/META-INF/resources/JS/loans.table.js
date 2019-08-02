var $table1 = $('#table1');
var $lookRepayPlan = $('#lookRepayPlan');
var $lookRepayFlow = $('#lookRepayFlow');
var $dqhk = $('#dqhk');
var $tqjqCur = $('#tqjqCur');
var $tqjqAll = $('#tqjqAll');

function initTable() {
    $table1.bootstrapTable({
    	url: "../loan/listLoans",
    	reinit: false,
    	classes: "table table-hover",
    	pagination: true,
    	toolbar: "#toolbar",
    	search: true,
    	striped: true,
    	responseHandler: function (res) {
            return res;
        },
        columns:
            [	
            	{
            		checkbox: true,
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
                    align: 'center'
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
                    align: 'center'
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
	    initTable2(loanId);
	});
}

function addButton3Event(){
	$lookRepayFlow.click(function () {
	    var loanId = getIdSelections();
	    if(loanId==""){
	    	layer.msg("至少选择一条", {icon: 5});
	    	return;
	    }
	    initTable3(loanId);
	});
}

function addButtonEvent_dqhk(){
	$lookRepayFlow.click(function () {
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
	initTable();
	addButton2Event();
	addButton3Event();
	
	addButtonEvent_dqhk();
	addButtonEvent_tqjqCur();
	addButtonEvent_tqjqAll();
});