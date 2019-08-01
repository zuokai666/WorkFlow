var $table2 = $('#table2');

function initTable2(loanId) {
    $table2.bootstrapTable({
    	url: "../loan/repayscheduleAction?loanId="+loanId,
    	reinit: false,
    	classes: "table table-hover",
    	pagination: true,
    	striped: true,
    	responseHandler: function (res) {
            return res;
        },
        columns:
            [
            	{
                    field: 'id',
                    title: '编号',
                    visiable: 'false'
                }, {
                    field: 'currentTerm',
                    title: '当前期数',
                    align: 'center'
                }, {
                    field: 'startDate',
                    title: '开始日期',
                    align: 'center'
                }, {
                    field: 'endDate',
                    title: '截止日期',
                    align: 'center'
                }, {
                    field: 'payDate',
                    title: '应付日',
                    align: 'center'
                }, {
                    field: 'repayPrincipal',
                    title: '应还本金',
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
                    field: 'finishDate',
                    title: '结清日期',
                    align: 'center'
                }, {
                    field: 'remark',
                    title: '备注',
                    align: 'center'
                }
            ]
    });
}