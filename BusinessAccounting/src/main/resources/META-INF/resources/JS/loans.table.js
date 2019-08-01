var $table1 = $('#table1');
var $lookRepayPlan = $('#lookRepayPlan');
var $lookRepayFlow = $('#lookRepayFlow');
var $update = $('#update');

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
                    field: 'repayInterest',
                    title: '应还利息',
                    align: 'center'
                }, {
                    field: 'repayAmount',
                    title: '应还金额',
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

$(function() {
	initTable();
	addButton2Event();
	addButton3Event();
});