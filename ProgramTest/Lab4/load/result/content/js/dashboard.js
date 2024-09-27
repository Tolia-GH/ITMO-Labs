/*
   Licensed to the Apache Software Foundation (ASF) under one or more
   contributor license agreements.  See the NOTICE file distributed with
   this work for additional information regarding copyright ownership.
   The ASF licenses this file to You under the Apache License, Version 2.0
   (the "License"); you may not use this file except in compliance with
   the License.  You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
var showControllersOnly = false;
var seriesFilter = "";
var filtersOnlySampleSeries = true;

/*
 * Add header in statistics table to group metrics by category
 * format
 *
 */
function summaryTableHeader(header) {
    var newRow = header.insertRow(-1);
    newRow.className = "tablesorter-no-sort";
    var cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 1;
    cell.innerHTML = "Requests";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 3;
    cell.innerHTML = "Executions";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 7;
    cell.innerHTML = "Response Times (ms)";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 1;
    cell.innerHTML = "Throughput";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 2;
    cell.innerHTML = "Network (KB/sec)";
    newRow.appendChild(cell);
}

/*
 * Populates the table identified by id parameter with the specified data and
 * format
 *
 */
function createTable(table, info, formatter, defaultSorts, seriesIndex, headerCreator) {
    var tableRef = table[0];

    // Create header and populate it with data.titles array
    var header = tableRef.createTHead();

    // Call callback is available
    if(headerCreator) {
        headerCreator(header);
    }

    var newRow = header.insertRow(-1);
    for (var index = 0; index < info.titles.length; index++) {
        var cell = document.createElement('th');
        cell.innerHTML = info.titles[index];
        newRow.appendChild(cell);
    }

    var tBody;

    // Create overall body if defined
    if(info.overall){
        tBody = document.createElement('tbody');
        tBody.className = "tablesorter-no-sort";
        tableRef.appendChild(tBody);
        var newRow = tBody.insertRow(-1);
        var data = info.overall.data;
        for(var index=0;index < data.length; index++){
            var cell = newRow.insertCell(-1);
            cell.innerHTML = formatter ? formatter(index, data[index]): data[index];
        }
    }

    // Create regular body
    tBody = document.createElement('tbody');
    tableRef.appendChild(tBody);

    var regexp;
    if(seriesFilter) {
        regexp = new RegExp(seriesFilter, 'i');
    }
    // Populate body with data.items array
    for(var index=0; index < info.items.length; index++){
        var item = info.items[index];
        if((!regexp || filtersOnlySampleSeries && !info.supportsControllersDiscrimination || regexp.test(item.data[seriesIndex]))
                &&
                (!showControllersOnly || !info.supportsControllersDiscrimination || item.isController)){
            if(item.data.length > 0) {
                var newRow = tBody.insertRow(-1);
                for(var col=0; col < item.data.length; col++){
                    var cell = newRow.insertCell(-1);
                    cell.innerHTML = formatter ? formatter(col, item.data[col]) : item.data[col];
                }
            }
        }
    }

    // Add support of columns sort
    table.tablesorter({sortList : defaultSorts});
}

$(document).ready(function() {

    // Customize table sorter default options
    $.extend( $.tablesorter.defaults, {
        theme: 'blue',
        cssInfoBlock: "tablesorter-no-sort",
        widthFixed: true,
        widgets: ['zebra']
    });

    var data = {"OkPercent": 56.96969696969697, "KoPercent": 43.03030303030303};
    var dataset = [
        {
            "label" : "FAIL",
            "data" : data.KoPercent,
            "color" : "#FF6347"
        },
        {
            "label" : "PASS",
            "data" : data.OkPercent,
            "color" : "#9ACD32"
        }];
    $.plot($("#flot-requests-summary"), dataset, {
        series : {
            pie : {
                show : true,
                radius : 1,
                label : {
                    show : true,
                    radius : 3 / 4,
                    formatter : function(label, series) {
                        return '<div style="font-size:8pt;text-align:center;padding:2px;color:white;">'
                            + label
                            + '<br/>'
                            + Math.round10(series.percent, -2)
                            + '%</div>';
                    },
                    background : {
                        opacity : 0.5,
                        color : '#000'
                    }
                }
            }
        },
        legend : {
            show : true
        }
    });

    // Creates APDEX table
    createTable($("#apdexTable"), {"supportsControllersDiscrimination": true, "overall": {"data": [0.3515151515151515, 500, 1500, "Total"], "isController": false}, "titles": ["Apdex", "T (Toleration threshold)", "F (Frustration threshold)", "Label"], "items": [{"data": [0.7, 500, 1500, "conf3"], "isController": false}, {"data": [0.35454545454545455, 500, 1500, "conf2"], "isController": false}, {"data": [0.0, 500, 1500, "conf1"], "isController": false}]}, function(index, item){
        switch(index){
            case 0:
                item = item.toFixed(3);
                break;
            case 1:
            case 2:
                item = formatDuration(item);
                break;
        }
        return item;
    }, [[0, 0]], 3);

    // Create statistics table
    createTable($("#statisticsTable"), {"supportsControllersDiscrimination": true, "overall": {"data": ["Total", 165, 71, 43.03030303030303, 898.987878787879, 424, 2022, 896.0, 1345.0, 1371.0, 1684.0800000000017, 20.759939607448416, 4.6831504387896326, 3.182920428095118], "isController": false}, "titles": ["Label", "#Samples", "FAIL", "Error %", "Average", "Min", "Max", "Median", "90th pct", "95th pct", "99th pct", "Transactions/s", "Received", "Sent"], "items": [{"data": ["conf3", 55, 0, 0.0, 507.1454545454547, 424, 823, 523.0, 553.0, 608.7999999999989, 823.0, 8.071617258585265, 1.8208433464191371, 1.2375428804666861], "isController": false}, {"data": ["conf2", 55, 16, 29.09090909090909, 893.5818181818183, 825, 962, 896.0, 957.4, 961.2, 962.0, 7.935362862501804, 1.790106270740153, 1.216652313879671], "isController": false}, {"data": ["conf1", 55, 55, 100.0, 1296.2363636363636, 1156, 2022, 1273.0, 1375.0, 1425.1999999999996, 2022.0, 6.919979869149472, 1.5610501462632107, 1.060973476031706], "isController": false}]}, function(index, item){
        switch(index){
            // Errors pct
            case 3:
                item = item.toFixed(2) + '%';
                break;
            // Mean
            case 4:
            // Mean
            case 7:
            // Median
            case 8:
            // Percentile 1
            case 9:
            // Percentile 2
            case 10:
            // Percentile 3
            case 11:
            // Throughput
            case 12:
            // Kbytes/s
            case 13:
            // Sent Kbytes/s
                item = item.toFixed(2);
                break;
        }
        return item;
    }, [[0, 0]], 0, summaryTableHeader);

    // Create error table
    createTable($("#errorsTable"), {"supportsControllersDiscrimination": false, "titles": ["Type of error", "Number of errors", "% in errors", "% in all samples"], "items": [{"data": ["The operation lasted too long: It took 953 milliseconds, but should not have lasted longer than 930 milliseconds.", 1, 1.408450704225352, 0.6060606060606061], "isController": false}, {"data": ["The operation lasted too long: It took 935 milliseconds, but should not have lasted longer than 930 milliseconds.", 2, 2.816901408450704, 1.2121212121212122], "isController": false}, {"data": ["The operation lasted too long: It took 1,375 milliseconds, but should not have lasted longer than 930 milliseconds.", 4, 5.633802816901408, 2.4242424242424243], "isController": false}, {"data": ["The operation lasted too long: It took 1,253 milliseconds, but should not have lasted longer than 930 milliseconds.", 2, 2.816901408450704, 1.2121212121212122], "isController": false}, {"data": ["The operation lasted too long: It took 2,022 milliseconds, but should not have lasted longer than 930 milliseconds.", 1, 1.408450704225352, 0.6060606060606061], "isController": false}, {"data": ["The operation lasted too long: It took 1,368 milliseconds, but should not have lasted longer than 930 milliseconds.", 1, 1.408450704225352, 0.6060606060606061], "isController": false}, {"data": ["The operation lasted too long: It took 1,156 milliseconds, but should not have lasted longer than 930 milliseconds.", 1, 1.408450704225352, 0.6060606060606061], "isController": false}, {"data": ["The operation lasted too long: It took 1,349 milliseconds, but should not have lasted longer than 930 milliseconds.", 2, 2.816901408450704, 1.2121212121212122], "isController": false}, {"data": ["The operation lasted too long: It took 1,346 milliseconds, but should not have lasted longer than 930 milliseconds.", 2, 2.816901408450704, 1.2121212121212122], "isController": false}, {"data": ["The operation lasted too long: It took 961 milliseconds, but should not have lasted longer than 930 milliseconds.", 3, 4.225352112676056, 1.8181818181818181], "isController": false}, {"data": ["The operation lasted too long: It took 1,510 milliseconds, but should not have lasted longer than 930 milliseconds.", 1, 1.408450704225352, 0.6060606060606061], "isController": false}, {"data": ["The operation lasted too long: It took 1,238 milliseconds, but should not have lasted longer than 930 milliseconds.", 2, 2.816901408450704, 1.2121212121212122], "isController": false}, {"data": ["The operation lasted too long: It took 1,256 milliseconds, but should not have lasted longer than 930 milliseconds.", 1, 1.408450704225352, 0.6060606060606061], "isController": false}, {"data": ["The operation lasted too long: It took 1,304 milliseconds, but should not have lasted longer than 930 milliseconds.", 5, 7.042253521126761, 3.0303030303030303], "isController": false}, {"data": ["The operation lasted too long: It took 1,262 milliseconds, but should not have lasted longer than 930 milliseconds.", 2, 2.816901408450704, 1.2121212121212122], "isController": false}, {"data": ["The operation lasted too long: It took 1,345 milliseconds, but should not have lasted longer than 930 milliseconds.", 2, 2.816901408450704, 1.2121212121212122], "isController": false}, {"data": ["The operation lasted too long: It took 962 milliseconds, but should not have lasted longer than 930 milliseconds.", 2, 2.816901408450704, 1.2121212121212122], "isController": false}, {"data": ["The operation lasted too long: It took 1,205 milliseconds, but should not have lasted longer than 930 milliseconds.", 4, 5.633802816901408, 2.4242424242424243], "isController": false}, {"data": ["The operation lasted too long: It took 1,303 milliseconds, but should not have lasted longer than 930 milliseconds.", 1, 1.408450704225352, 0.6060606060606061], "isController": false}, {"data": ["The operation lasted too long: It took 1,234 milliseconds, but should not have lasted longer than 930 milliseconds.", 1, 1.408450704225352, 0.6060606060606061], "isController": false}, {"data": ["The operation lasted too long: It took 937 milliseconds, but should not have lasted longer than 930 milliseconds.", 1, 1.408450704225352, 0.6060606060606061], "isController": false}, {"data": ["The operation lasted too long: It took 955 milliseconds, but should not have lasted longer than 930 milliseconds.", 2, 2.816901408450704, 1.2121212121212122], "isController": false}, {"data": ["The operation lasted too long: It took 1,237 milliseconds, but should not have lasted longer than 930 milliseconds.", 3, 4.225352112676056, 1.8181818181818181], "isController": false}, {"data": ["The operation lasted too long: It took 1,370 milliseconds, but should not have lasted longer than 930 milliseconds.", 1, 1.408450704225352, 0.6060606060606061], "isController": false}, {"data": ["The operation lasted too long: It took 934 milliseconds, but should not have lasted longer than 930 milliseconds.", 2, 2.816901408450704, 1.2121212121212122], "isController": false}, {"data": ["The operation lasted too long: It took 1,254 milliseconds, but should not have lasted longer than 930 milliseconds.", 1, 1.408450704225352, 0.6060606060606061], "isController": false}, {"data": ["The operation lasted too long: It took 1,404 milliseconds, but should not have lasted longer than 930 milliseconds.", 1, 1.408450704225352, 0.6060606060606061], "isController": false}, {"data": ["The operation lasted too long: It took 1,371 milliseconds, but should not have lasted longer than 930 milliseconds.", 2, 2.816901408450704, 1.2121212121212122], "isController": false}, {"data": ["The operation lasted too long: It took 1,263 milliseconds, but should not have lasted longer than 930 milliseconds.", 2, 2.816901408450704, 1.2121212121212122], "isController": false}, {"data": ["The operation lasted too long: It took 954 milliseconds, but should not have lasted longer than 930 milliseconds.", 2, 2.816901408450704, 1.2121212121212122], "isController": false}, {"data": ["The operation lasted too long: It took 1,239 milliseconds, but should not have lasted longer than 930 milliseconds.", 1, 1.408450704225352, 0.6060606060606061], "isController": false}, {"data": ["The operation lasted too long: It took 933 milliseconds, but should not have lasted longer than 930 milliseconds.", 1, 1.408450704225352, 0.6060606060606061], "isController": false}, {"data": ["The operation lasted too long: It took 1,273 milliseconds, but should not have lasted longer than 930 milliseconds.", 5, 7.042253521126761, 3.0303030303030303], "isController": false}, {"data": ["The operation lasted too long: It took 1,255 milliseconds, but should not have lasted longer than 930 milliseconds.", 1, 1.408450704225352, 0.6060606060606061], "isController": false}, {"data": ["The operation lasted too long: It took 1,252 milliseconds, but should not have lasted longer than 930 milliseconds.", 1, 1.408450704225352, 0.6060606060606061], "isController": false}, {"data": ["The operation lasted too long: It took 1,180 milliseconds, but should not have lasted longer than 930 milliseconds.", 5, 7.042253521126761, 3.0303030303030303], "isController": false}]}, function(index, item){
        switch(index){
            case 2:
            case 3:
                item = item.toFixed(2) + '%';
                break;
        }
        return item;
    }, [[1, 1]]);

        // Create top5 errors by sampler
    createTable($("#top5ErrorsBySamplerTable"), {"supportsControllersDiscrimination": false, "overall": {"data": ["Total", 165, 71, "The operation lasted too long: It took 1,304 milliseconds, but should not have lasted longer than 930 milliseconds.", 5, "The operation lasted too long: It took 1,273 milliseconds, but should not have lasted longer than 930 milliseconds.", 5, "The operation lasted too long: It took 1,180 milliseconds, but should not have lasted longer than 930 milliseconds.", 5, "The operation lasted too long: It took 1,375 milliseconds, but should not have lasted longer than 930 milliseconds.", 4, "The operation lasted too long: It took 1,205 milliseconds, but should not have lasted longer than 930 milliseconds.", 4], "isController": false}, "titles": ["Sample", "#Samples", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors"], "items": [{"data": [], "isController": false}, {"data": ["conf2", 55, 16, "The operation lasted too long: It took 961 milliseconds, but should not have lasted longer than 930 milliseconds.", 3, "The operation lasted too long: It took 935 milliseconds, but should not have lasted longer than 930 milliseconds.", 2, "The operation lasted too long: It took 962 milliseconds, but should not have lasted longer than 930 milliseconds.", 2, "The operation lasted too long: It took 954 milliseconds, but should not have lasted longer than 930 milliseconds.", 2, "The operation lasted too long: It took 955 milliseconds, but should not have lasted longer than 930 milliseconds.", 2], "isController": false}, {"data": ["conf1", 55, 55, "The operation lasted too long: It took 1,304 milliseconds, but should not have lasted longer than 930 milliseconds.", 5, "The operation lasted too long: It took 1,273 milliseconds, but should not have lasted longer than 930 milliseconds.", 5, "The operation lasted too long: It took 1,180 milliseconds, but should not have lasted longer than 930 milliseconds.", 5, "The operation lasted too long: It took 1,375 milliseconds, but should not have lasted longer than 930 milliseconds.", 4, "The operation lasted too long: It took 1,205 milliseconds, but should not have lasted longer than 930 milliseconds.", 4], "isController": false}]}, function(index, item){
        return item;
    }, [[0, 0]], 0);

});
