<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <!--Load the AJAX API-->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    <script type="text/javascript">

      // Load the Visualization API and the corechart package.
      google.charts.load('current', {'packages':['corechart']});

      // Set a callback to run when the Google Visualization API is loaded.
      google.charts.setOnLoadCallback(drawChart);

      // Callback that creates and populates a data table,
      // instantiates the pie chart, passes in the data and
      // draws it.
      function drawChart() {

        // Create the data table.
        var data = new google.visualization.DataTable();
        data.addColumn('string', '부서');
        data.addColumn('number', '인원수');
        
        var arr = [];
        //ajax
        $.ajax({ //아작스는 기본이 비동기라서 데이터 들어오기 전에 data.addRows(arr); 처리해버림 그렇기 때문에 여기에선 동기로 처리해야함
        	url: 'getChartData',
        	async : false,
        	dataType: 'json',
        	success: function(result) {
        		//서버에서 받아온 데이터 [{},{}] -> [[],[]]
        		for(o of result) {
        			arr.push ( [ o.SALE_DATE, o.SUM ] )
        		}
        	}
        })
        
        data.addRows(arr);

        // Set chart options
        var options = {'title':'일별 판매내역',
                       'width':400,
                       'height':300,
                       vAxis: { format: "$#,###", gridlines: { count: 10 } },
         colors: ['#e0440e', '#e6693e', '#ec8f6e', '#f3b49f', '#f6c7b6']
         
                       };
        // Instantiate and draw our chart, passing in some options.
        //차트 종류 변경 가능(LineChart, PieChart, BarChart, ColumnChart 등등)
        var chart = new google.visualization.ColumnChart(document.getElementById('chart_div'));
        chart.draw(data, options);
      }
    </script>
  </head>

  <body>
    <!--Div that will hold the pie chart-->
    <div id="chart_div"></div>
  </body>
</html>