var $table = $('#table');
var $remove = $('#remove');
var $add = $('#add');
var $update = $('#update');

function initTable() {
    $table.bootstrapTable({
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
    return $.map($table.bootstrapTable('getSelections'), function (row) {
        return row.id
    });
}

function addButtonEvent(){
	$remove.click(function () {
	    var ids = getIdSelections();
	    if(ids==""){
	    	layer.msg("至少选择一条", {icon: 5});
	    	return;
	    }
	    $.ajax({
		   type: "POST",
		   url: "../goodsType/delInBoxMessage",
		   data:"ids="+ids,
		   success: function(msg){
			   if(msg.result=="1"){
				   layer.msg(msg.tip, {icon: 6,time:1000},function(){
					   $table.bootstrapTable('remove', {
					        field: 'id',
					        values: ids
					   });
				   });
			   }else{
				   layer.msg(msg.tip, {icon: 5});
			   }
		   }
 		});
	});
}

$(function() {
	initTable();
	
});