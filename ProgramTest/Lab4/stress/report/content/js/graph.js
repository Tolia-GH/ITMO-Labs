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
$(document).ready(function() {

    $(".click-title").mouseenter( function(    e){
        e.preventDefault();
        this.style.cursor="pointer";
    });
    $(".click-title").mousedown( function(event){
        event.preventDefault();
    });

    // Ugly code while this script is shared among several pages
    try{
        refreshHitsPerSecond(true);
    } catch(e){}
    try{
        refreshResponseTimeOverTime(true);
    } catch(e){}
    try{
        refreshResponseTimePercentiles();
    } catch(e){}
});


var responseTimePercentilesInfos = {
        data: {"result": {"minY": 385.0, "minX": 0.0, "maxY": 1015.0, "series": [{"data": [[0.0, 385.0], [0.1, 385.0], [0.2, 385.0], [0.3, 385.0], [0.4, 385.0], [0.5, 385.0], [0.6, 392.0], [0.7, 392.0], [0.8, 392.0], [0.9, 392.0], [1.0, 392.0], [1.1, 393.0], [1.2, 393.0], [1.3, 393.0], [1.4, 393.0], [1.5, 393.0], [1.6, 399.0], [1.7, 399.0], [1.8, 399.0], [1.9, 399.0], [2.0, 399.0], [2.1, 399.0], [2.2, 399.0], [2.3, 399.0], [2.4, 399.0], [2.5, 399.0], [2.6, 399.0], [2.7, 402.0], [2.8, 402.0], [2.9, 402.0], [3.0, 402.0], [3.1, 402.0], [3.2, 404.0], [3.3, 404.0], [3.4, 404.0], [3.5, 404.0], [3.6, 404.0], [3.7, 409.0], [3.8, 409.0], [3.9, 409.0], [4.0, 409.0], [4.1, 409.0], [4.2, 409.0], [4.3, 409.0], [4.4, 409.0], [4.5, 409.0], [4.6, 409.0], [4.7, 409.0], [4.8, 415.0], [4.9, 415.0], [5.0, 415.0], [5.1, 415.0], [5.2, 415.0], [5.3, 422.0], [5.4, 422.0], [5.5, 422.0], [5.6, 422.0], [5.7, 422.0], [5.8, 428.0], [5.9, 428.0], [6.0, 428.0], [6.1, 428.0], [6.2, 428.0], [6.3, 428.0], [6.4, 430.0], [6.5, 430.0], [6.6, 430.0], [6.7, 430.0], [6.8, 430.0], [6.9, 433.0], [7.0, 433.0], [7.1, 433.0], [7.2, 433.0], [7.3, 433.0], [7.4, 434.0], [7.5, 434.0], [7.6, 434.0], [7.7, 434.0], [7.8, 434.0], [7.9, 436.0], [8.0, 436.0], [8.1, 436.0], [8.2, 436.0], [8.3, 436.0], [8.4, 436.0], [8.5, 441.0], [8.6, 441.0], [8.7, 441.0], [8.8, 441.0], [8.9, 441.0], [9.0, 446.0], [9.1, 446.0], [9.2, 446.0], [9.3, 446.0], [9.4, 446.0], [9.5, 446.0], [9.6, 446.0], [9.7, 446.0], [9.8, 446.0], [9.9, 446.0], [10.0, 446.0], [10.1, 446.0], [10.2, 446.0], [10.3, 446.0], [10.4, 446.0], [10.5, 446.0], [10.6, 447.0], [10.7, 447.0], [10.8, 447.0], [10.9, 447.0], [11.0, 447.0], [11.1, 449.0], [11.2, 449.0], [11.3, 449.0], [11.4, 449.0], [11.5, 449.0], [11.6, 450.0], [11.7, 450.0], [11.8, 450.0], [11.9, 450.0], [12.0, 450.0], [12.1, 450.0], [12.2, 454.0], [12.3, 454.0], [12.4, 454.0], [12.5, 454.0], [12.6, 454.0], [12.7, 455.0], [12.8, 455.0], [12.9, 455.0], [13.0, 455.0], [13.1, 455.0], [13.2, 456.0], [13.3, 456.0], [13.4, 456.0], [13.5, 456.0], [13.6, 456.0], [13.7, 456.0], [13.8, 456.0], [13.9, 456.0], [14.0, 456.0], [14.1, 456.0], [14.2, 456.0], [14.3, 457.0], [14.4, 457.0], [14.5, 457.0], [14.6, 457.0], [14.7, 457.0], [14.8, 457.0], [14.9, 457.0], [15.0, 457.0], [15.1, 457.0], [15.2, 457.0], [15.3, 463.0], [15.4, 463.0], [15.5, 463.0], [15.6, 463.0], [15.7, 463.0], [15.8, 463.0], [15.9, 463.0], [16.0, 463.0], [16.1, 463.0], [16.2, 463.0], [16.3, 463.0], [16.4, 464.0], [16.5, 464.0], [16.6, 464.0], [16.7, 464.0], [16.8, 464.0], [16.9, 466.0], [17.0, 466.0], [17.1, 466.0], [17.2, 466.0], [17.3, 466.0], [17.4, 466.0], [17.5, 466.0], [17.6, 466.0], [17.7, 466.0], [17.8, 466.0], [17.9, 469.0], [18.0, 469.0], [18.1, 469.0], [18.2, 469.0], [18.3, 469.0], [18.4, 469.0], [18.5, 472.0], [18.6, 472.0], [18.7, 472.0], [18.8, 472.0], [18.9, 472.0], [19.0, 473.0], [19.1, 473.0], [19.2, 473.0], [19.3, 473.0], [19.4, 473.0], [19.5, 476.0], [19.6, 476.0], [19.7, 476.0], [19.8, 476.0], [19.9, 476.0], [20.0, 476.0], [20.1, 480.0], [20.2, 480.0], [20.3, 480.0], [20.4, 480.0], [20.5, 480.0], [20.6, 481.0], [20.7, 481.0], [20.8, 481.0], [20.9, 481.0], [21.0, 481.0], [21.1, 483.0], [21.2, 483.0], [21.3, 483.0], [21.4, 483.0], [21.5, 483.0], [21.6, 488.0], [21.7, 488.0], [21.8, 488.0], [21.9, 488.0], [22.0, 488.0], [22.1, 488.0], [22.2, 488.0], [22.3, 488.0], [22.4, 488.0], [22.5, 488.0], [22.6, 488.0], [22.7, 492.0], [22.8, 492.0], [22.9, 492.0], [23.0, 492.0], [23.1, 492.0], [23.2, 494.0], [23.3, 494.0], [23.4, 494.0], [23.5, 494.0], [23.6, 494.0], [23.7, 495.0], [23.8, 495.0], [23.9, 495.0], [24.0, 495.0], [24.1, 495.0], [24.2, 495.0], [24.3, 496.0], [24.4, 496.0], [24.5, 496.0], [24.6, 496.0], [24.7, 496.0], [24.8, 496.0], [24.9, 496.0], [25.0, 496.0], [25.1, 496.0], [25.2, 496.0], [25.3, 498.0], [25.4, 498.0], [25.5, 498.0], [25.6, 498.0], [25.7, 498.0], [25.8, 499.0], [25.9, 499.0], [26.0, 499.0], [26.1, 499.0], [26.2, 499.0], [26.3, 499.0], [26.4, 501.0], [26.5, 501.0], [26.6, 501.0], [26.7, 501.0], [26.8, 501.0], [26.9, 502.0], [27.0, 502.0], [27.1, 502.0], [27.2, 502.0], [27.3, 502.0], [27.4, 502.0], [27.5, 502.0], [27.6, 502.0], [27.7, 502.0], [27.8, 502.0], [27.9, 502.0], [28.0, 502.0], [28.1, 502.0], [28.2, 502.0], [28.3, 502.0], [28.4, 502.0], [28.5, 505.0], [28.6, 505.0], [28.7, 505.0], [28.8, 505.0], [28.9, 505.0], [29.0, 506.0], [29.1, 506.0], [29.2, 506.0], [29.3, 506.0], [29.4, 506.0], [29.5, 511.0], [29.6, 511.0], [29.7, 511.0], [29.8, 511.0], [29.9, 511.0], [30.0, 511.0], [30.1, 513.0], [30.2, 513.0], [30.3, 513.0], [30.4, 513.0], [30.5, 513.0], [30.6, 513.0], [30.7, 513.0], [30.8, 513.0], [30.9, 513.0], [31.0, 513.0], [31.1, 515.0], [31.2, 515.0], [31.3, 515.0], [31.4, 515.0], [31.5, 515.0], [31.6, 520.0], [31.7, 520.0], [31.8, 520.0], [31.9, 520.0], [32.0, 520.0], [32.1, 520.0], [32.2, 520.0], [32.3, 520.0], [32.4, 520.0], [32.5, 520.0], [32.6, 520.0], [32.7, 521.0], [32.8, 521.0], [32.9, 521.0], [33.0, 521.0], [33.1, 521.0], [33.2, 522.0], [33.3, 522.0], [33.4, 522.0], [33.5, 522.0], [33.6, 522.0], [33.7, 523.0], [33.8, 523.0], [33.9, 523.0], [34.0, 523.0], [34.1, 523.0], [34.2, 523.0], [34.3, 524.0], [34.4, 524.0], [34.5, 524.0], [34.6, 524.0], [34.7, 524.0], [34.8, 524.0], [34.9, 524.0], [35.0, 524.0], [35.1, 524.0], [35.2, 524.0], [35.3, 526.0], [35.4, 526.0], [35.5, 526.0], [35.6, 526.0], [35.7, 526.0], [35.8, 528.0], [35.9, 528.0], [36.0, 528.0], [36.1, 528.0], [36.2, 528.0], [36.3, 528.0], [36.4, 529.0], [36.5, 529.0], [36.6, 529.0], [36.7, 529.0], [36.8, 529.0], [36.9, 530.0], [37.0, 530.0], [37.1, 530.0], [37.2, 530.0], [37.3, 530.0], [37.4, 533.0], [37.5, 533.0], [37.6, 533.0], [37.7, 533.0], [37.8, 533.0], [37.9, 535.0], [38.0, 535.0], [38.1, 535.0], [38.2, 535.0], [38.3, 535.0], [38.4, 535.0], [38.5, 536.0], [38.6, 536.0], [38.7, 536.0], [38.8, 536.0], [38.9, 536.0], [39.0, 541.0], [39.1, 541.0], [39.2, 541.0], [39.3, 541.0], [39.4, 541.0], [39.5, 542.0], [39.6, 542.0], [39.7, 542.0], [39.8, 542.0], [39.9, 542.0], [40.0, 542.0], [40.1, 545.0], [40.2, 545.0], [40.3, 545.0], [40.4, 545.0], [40.5, 545.0], [40.6, 551.0], [40.7, 551.0], [40.8, 551.0], [40.9, 551.0], [41.0, 551.0], [41.1, 553.0], [41.2, 553.0], [41.3, 553.0], [41.4, 553.0], [41.5, 553.0], [41.6, 557.0], [41.7, 557.0], [41.8, 557.0], [41.9, 557.0], [42.0, 557.0], [42.1, 557.0], [42.2, 558.0], [42.3, 558.0], [42.4, 558.0], [42.5, 558.0], [42.6, 558.0], [42.7, 561.0], [42.8, 561.0], [42.9, 561.0], [43.0, 561.0], [43.1, 561.0], [43.2, 562.0], [43.3, 562.0], [43.4, 562.0], [43.5, 562.0], [43.6, 562.0], [43.7, 564.0], [43.8, 564.0], [43.9, 564.0], [44.0, 564.0], [44.1, 564.0], [44.2, 564.0], [44.3, 565.0], [44.4, 565.0], [44.5, 565.0], [44.6, 565.0], [44.7, 565.0], [44.8, 567.0], [44.9, 567.0], [45.0, 567.0], [45.1, 567.0], [45.2, 567.0], [45.3, 569.0], [45.4, 569.0], [45.5, 569.0], [45.6, 569.0], [45.7, 569.0], [45.8, 569.0], [45.9, 569.0], [46.0, 569.0], [46.1, 569.0], [46.2, 569.0], [46.3, 569.0], [46.4, 572.0], [46.5, 572.0], [46.6, 572.0], [46.7, 572.0], [46.8, 572.0], [46.9, 573.0], [47.0, 573.0], [47.1, 573.0], [47.2, 573.0], [47.3, 573.0], [47.4, 579.0], [47.5, 579.0], [47.6, 579.0], [47.7, 579.0], [47.8, 579.0], [47.9, 580.0], [48.0, 580.0], [48.1, 580.0], [48.2, 580.0], [48.3, 580.0], [48.4, 580.0], [48.5, 583.0], [48.6, 583.0], [48.7, 583.0], [48.8, 583.0], [48.9, 583.0], [49.0, 589.0], [49.1, 589.0], [49.2, 589.0], [49.3, 589.0], [49.4, 589.0], [49.5, 591.0], [49.6, 591.0], [49.7, 591.0], [49.8, 591.0], [49.9, 591.0], [50.0, 591.0], [50.1, 593.0], [50.2, 593.0], [50.3, 593.0], [50.4, 593.0], [50.5, 593.0], [50.6, 596.0], [50.7, 596.0], [50.8, 596.0], [50.9, 596.0], [51.0, 596.0], [51.1, 599.0], [51.2, 599.0], [51.3, 599.0], [51.4, 599.0], [51.5, 599.0], [51.6, 602.0], [51.7, 602.0], [51.8, 602.0], [51.9, 602.0], [52.0, 602.0], [52.1, 602.0], [52.2, 602.0], [52.3, 602.0], [52.4, 602.0], [52.5, 602.0], [52.6, 602.0], [52.7, 604.0], [52.8, 604.0], [52.9, 604.0], [53.0, 604.0], [53.1, 604.0], [53.2, 607.0], [53.3, 607.0], [53.4, 607.0], [53.5, 607.0], [53.6, 607.0], [53.7, 609.0], [53.8, 609.0], [53.9, 609.0], [54.0, 609.0], [54.1, 609.0], [54.2, 609.0], [54.3, 609.0], [54.4, 609.0], [54.5, 609.0], [54.6, 609.0], [54.7, 609.0], [54.8, 612.0], [54.9, 612.0], [55.0, 612.0], [55.1, 612.0], [55.2, 612.0], [55.3, 616.0], [55.4, 616.0], [55.5, 616.0], [55.6, 616.0], [55.7, 616.0], [55.8, 618.0], [55.9, 618.0], [56.0, 618.0], [56.1, 618.0], [56.2, 618.0], [56.3, 618.0], [56.4, 619.0], [56.5, 619.0], [56.6, 619.0], [56.7, 619.0], [56.8, 619.0], [56.9, 620.0], [57.0, 620.0], [57.1, 620.0], [57.2, 620.0], [57.3, 620.0], [57.4, 626.0], [57.5, 626.0], [57.6, 626.0], [57.7, 626.0], [57.8, 626.0], [57.9, 628.0], [58.0, 628.0], [58.1, 628.0], [58.2, 628.0], [58.3, 628.0], [58.4, 628.0], [58.5, 629.0], [58.6, 629.0], [58.7, 629.0], [58.8, 629.0], [58.9, 629.0], [59.0, 629.0], [59.1, 629.0], [59.2, 629.0], [59.3, 629.0], [59.4, 629.0], [59.5, 633.0], [59.6, 633.0], [59.7, 633.0], [59.8, 633.0], [59.9, 633.0], [60.0, 633.0], [60.1, 635.0], [60.2, 635.0], [60.3, 635.0], [60.4, 635.0], [60.5, 635.0], [60.6, 637.0], [60.7, 637.0], [60.8, 637.0], [60.9, 637.0], [61.0, 637.0], [61.1, 640.0], [61.2, 640.0], [61.3, 640.0], [61.4, 640.0], [61.5, 640.0], [61.6, 642.0], [61.7, 642.0], [61.8, 642.0], [61.9, 642.0], [62.0, 642.0], [62.1, 642.0], [62.2, 643.0], [62.3, 643.0], [62.4, 643.0], [62.5, 643.0], [62.6, 643.0], [62.7, 648.0], [62.8, 648.0], [62.9, 648.0], [63.0, 648.0], [63.1, 648.0], [63.2, 651.0], [63.3, 651.0], [63.4, 651.0], [63.5, 651.0], [63.6, 651.0], [63.7, 654.0], [63.8, 654.0], [63.9, 654.0], [64.0, 654.0], [64.1, 654.0], [64.2, 654.0], [64.3, 656.0], [64.4, 656.0], [64.5, 656.0], [64.6, 656.0], [64.7, 656.0], [64.8, 658.0], [64.9, 658.0], [65.0, 658.0], [65.1, 658.0], [65.2, 658.0], [65.3, 660.0], [65.4, 660.0], [65.5, 660.0], [65.6, 660.0], [65.7, 660.0], [65.8, 668.0], [65.9, 668.0], [66.0, 668.0], [66.1, 668.0], [66.2, 668.0], [66.3, 668.0], [66.4, 669.0], [66.5, 669.0], [66.6, 669.0], [66.7, 669.0], [66.8, 669.0], [66.9, 674.0], [67.0, 674.0], [67.1, 674.0], [67.2, 674.0], [67.3, 674.0], [67.4, 680.0], [67.5, 680.0], [67.6, 680.0], [67.7, 680.0], [67.8, 680.0], [67.9, 685.0], [68.0, 685.0], [68.1, 685.0], [68.2, 685.0], [68.3, 685.0], [68.4, 685.0], [68.5, 690.0], [68.6, 690.0], [68.7, 690.0], [68.8, 690.0], [68.9, 690.0], [69.0, 704.0], [69.1, 704.0], [69.2, 704.0], [69.3, 704.0], [69.4, 704.0], [69.5, 718.0], [69.6, 718.0], [69.7, 718.0], [69.8, 718.0], [69.9, 718.0], [70.0, 718.0], [70.1, 724.0], [70.2, 724.0], [70.3, 724.0], [70.4, 724.0], [70.5, 724.0], [70.6, 725.0], [70.7, 725.0], [70.8, 725.0], [70.9, 725.0], [71.0, 725.0], [71.1, 735.0], [71.2, 735.0], [71.3, 735.0], [71.4, 735.0], [71.5, 735.0], [71.6, 735.0], [71.7, 735.0], [71.8, 735.0], [71.9, 735.0], [72.0, 735.0], [72.1, 735.0], [72.2, 744.0], [72.3, 744.0], [72.4, 744.0], [72.5, 744.0], [72.6, 744.0], [72.7, 746.0], [72.8, 746.0], [72.9, 746.0], [73.0, 746.0], [73.1, 746.0], [73.2, 746.0], [73.3, 746.0], [73.4, 746.0], [73.5, 746.0], [73.6, 746.0], [73.7, 750.0], [73.8, 750.0], [73.9, 750.0], [74.0, 750.0], [74.1, 750.0], [74.2, 750.0], [74.3, 755.0], [74.4, 755.0], [74.5, 755.0], [74.6, 755.0], [74.7, 755.0], [74.8, 779.0], [74.9, 779.0], [75.0, 779.0], [75.1, 779.0], [75.2, 779.0], [75.3, 790.0], [75.4, 790.0], [75.5, 790.0], [75.6, 790.0], [75.7, 790.0], [75.8, 790.0], [75.9, 790.0], [76.0, 790.0], [76.1, 790.0], [76.2, 790.0], [76.3, 790.0], [76.4, 794.0], [76.5, 794.0], [76.6, 794.0], [76.7, 794.0], [76.8, 794.0], [76.9, 798.0], [77.0, 798.0], [77.1, 798.0], [77.2, 798.0], [77.3, 798.0], [77.4, 799.0], [77.5, 799.0], [77.6, 799.0], [77.7, 799.0], [77.8, 799.0], [77.9, 800.0], [78.0, 800.0], [78.1, 800.0], [78.2, 800.0], [78.3, 800.0], [78.4, 800.0], [78.5, 801.0], [78.6, 801.0], [78.7, 801.0], [78.8, 801.0], [78.9, 801.0], [79.0, 802.0], [79.1, 802.0], [79.2, 802.0], [79.3, 802.0], [79.4, 802.0], [79.5, 809.0], [79.6, 809.0], [79.7, 809.0], [79.8, 809.0], [79.9, 809.0], [80.0, 809.0], [80.1, 811.0], [80.2, 811.0], [80.3, 811.0], [80.4, 811.0], [80.5, 811.0], [80.6, 813.0], [80.7, 813.0], [80.8, 813.0], [80.9, 813.0], [81.0, 813.0], [81.1, 813.0], [81.2, 813.0], [81.3, 813.0], [81.4, 813.0], [81.5, 813.0], [81.6, 814.0], [81.7, 814.0], [81.8, 814.0], [81.9, 814.0], [82.0, 814.0], [82.1, 814.0], [82.2, 819.0], [82.3, 819.0], [82.4, 819.0], [82.5, 819.0], [82.6, 819.0], [82.7, 822.0], [82.8, 822.0], [82.9, 822.0], [83.0, 822.0], [83.1, 822.0], [83.2, 824.0], [83.3, 824.0], [83.4, 824.0], [83.5, 824.0], [83.6, 824.0], [83.7, 826.0], [83.8, 826.0], [83.9, 826.0], [84.0, 826.0], [84.1, 826.0], [84.2, 826.0], [84.3, 828.0], [84.4, 828.0], [84.5, 828.0], [84.6, 828.0], [84.7, 828.0], [84.8, 830.0], [84.9, 830.0], [85.0, 830.0], [85.1, 830.0], [85.2, 830.0], [85.3, 831.0], [85.4, 831.0], [85.5, 831.0], [85.6, 831.0], [85.7, 831.0], [85.8, 835.0], [85.9, 835.0], [86.0, 835.0], [86.1, 835.0], [86.2, 835.0], [86.3, 835.0], [86.4, 835.0], [86.5, 835.0], [86.6, 835.0], [86.7, 835.0], [86.8, 835.0], [86.9, 840.0], [87.0, 840.0], [87.1, 840.0], [87.2, 840.0], [87.3, 840.0], [87.4, 842.0], [87.5, 842.0], [87.6, 842.0], [87.7, 842.0], [87.8, 842.0], [87.9, 846.0], [88.0, 846.0], [88.1, 846.0], [88.2, 846.0], [88.3, 846.0], [88.4, 846.0], [88.5, 846.0], [88.6, 846.0], [88.7, 846.0], [88.8, 846.0], [88.9, 846.0], [89.0, 851.0], [89.1, 851.0], [89.2, 851.0], [89.3, 851.0], [89.4, 851.0], [89.5, 857.0], [89.6, 857.0], [89.7, 857.0], [89.8, 857.0], [89.9, 857.0], [90.0, 857.0], [90.1, 875.0], [90.2, 875.0], [90.3, 875.0], [90.4, 875.0], [90.5, 875.0], [90.6, 875.0], [90.7, 875.0], [90.8, 875.0], [90.9, 875.0], [91.0, 875.0], [91.1, 882.0], [91.2, 882.0], [91.3, 882.0], [91.4, 882.0], [91.5, 882.0], [91.6, 885.0], [91.7, 885.0], [91.8, 885.0], [91.9, 885.0], [92.0, 885.0], [92.1, 885.0], [92.2, 888.0], [92.3, 888.0], [92.4, 888.0], [92.5, 888.0], [92.6, 888.0], [92.7, 893.0], [92.8, 893.0], [92.9, 893.0], [93.0, 893.0], [93.1, 893.0], [93.2, 895.0], [93.3, 895.0], [93.4, 895.0], [93.5, 895.0], [93.6, 895.0], [93.7, 897.0], [93.8, 897.0], [93.9, 897.0], [94.0, 897.0], [94.1, 897.0], [94.2, 897.0], [94.3, 903.0], [94.4, 903.0], [94.5, 903.0], [94.6, 903.0], [94.7, 903.0], [94.8, 906.0], [94.9, 906.0], [95.0, 906.0], [95.1, 906.0], [95.2, 906.0], [95.3, 912.0], [95.4, 912.0], [95.5, 912.0], [95.6, 912.0], [95.7, 912.0], [95.8, 914.0], [95.9, 914.0], [96.0, 914.0], [96.1, 914.0], [96.2, 914.0], [96.3, 914.0], [96.4, 939.0], [96.5, 939.0], [96.6, 939.0], [96.7, 939.0], [96.8, 939.0], [96.9, 943.0], [97.0, 943.0], [97.1, 943.0], [97.2, 943.0], [97.3, 943.0], [97.4, 944.0], [97.5, 944.0], [97.6, 944.0], [97.7, 944.0], [97.8, 944.0], [97.9, 955.0], [98.0, 955.0], [98.1, 955.0], [98.2, 955.0], [98.3, 955.0], [98.4, 955.0], [98.5, 960.0], [98.6, 960.0], [98.7, 960.0], [98.8, 960.0], [98.9, 960.0], [99.0, 973.0], [99.1, 973.0], [99.2, 973.0], [99.3, 973.0], [99.4, 973.0], [99.5, 1015.0], [99.6, 1015.0], [99.7, 1015.0], [99.8, 1015.0], [99.9, 1015.0], [100.0, 1015.0]], "isOverall": false, "label": "HTTP Request", "isController": false}], "supportsControllersDiscrimination": true, "maxX": 100.0, "title": "Response Time Percentiles"}},
        getOptions: function() {
            return {
                series: {
                    points: { show: false }
                },
                legend: {
                    noColumns: 2,
                    show: true,
                    container: '#legendResponseTimePercentiles'
                },
                xaxis: {
                    tickDecimals: 1,
                    axisLabel: "Percentiles",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                yaxis: {
                    axisLabel: "Percentile value in ms",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20
                },
                grid: {
                    hoverable: true // IMPORTANT! this is needed for tooltip to
                                    // work
                },
                tooltip: true,
                tooltipOpts: {
                    content: "%s : %x.2 percentile was %y ms"
                },
                selection: { mode: "xy" },
            };
        },
        createGraph: function() {
            var data = this.data;
            var dataset = prepareData(data.result.series, $("#choicesResponseTimePercentiles"));
            var options = this.getOptions();
            prepareOptions(options, data);
            $.plot($("#flotResponseTimesPercentiles"), dataset, options);
            // setup overview
            $.plot($("#overviewResponseTimesPercentiles"), dataset, prepareOverviewOptions(options));
        }
};

/**
 * @param elementId Id of element where we display message
 */
function setEmptyGraph(elementId) {
    $(function() {
        $(elementId).text("No graph series with filter="+seriesFilter);
    });
}

// Response times percentiles
function refreshResponseTimePercentiles() {
    var infos = responseTimePercentilesInfos;
    prepareSeries(infos.data);
    if(infos.data.result.series.length == 0) {
        setEmptyGraph("#bodyResponseTimePercentiles");
        return;
    }
    if (isGraph($("#flotResponseTimesPercentiles"))){
        infos.createGraph();
    } else {
        var choiceContainer = $("#choicesResponseTimePercentiles");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        setGraphZoomable("#flotResponseTimesPercentiles", "#overviewResponseTimesPercentiles");
        $('#bodyResponseTimePercentiles .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
}

var responseTimeDistributionInfos = {
        data: {"result": {"minY": 1.0, "minX": 300.0, "maxY": 48.0, "series": [{"data": [[300.0, 5.0], [600.0, 33.0], [700.0, 17.0], [400.0, 45.0], [800.0, 31.0], [900.0, 10.0], [500.0, 48.0], [1000.0, 1.0]], "isOverall": false, "label": "HTTP Request", "isController": false}], "supportsControllersDiscrimination": true, "granularity": 100, "maxX": 1000.0, "title": "Response Time Distribution"}},
        getOptions: function() {
            var granularity = this.data.result.granularity;
            return {
                legend: {
                    noColumns: 2,
                    show: true,
                    container: '#legendResponseTimeDistribution'
                },
                xaxis:{
                    axisLabel: "Response times in ms",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                yaxis: {
                    axisLabel: "Number of responses",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                bars : {
                    show: true,
                    barWidth: this.data.result.granularity
                },
                grid: {
                    hoverable: true // IMPORTANT! this is needed for tooltip to
                                    // work
                },
                tooltip: true,
                tooltipOpts: {
                    content: function(label, xval, yval, flotItem){
                        return yval + " responses for " + label + " were between " + xval + " and " + (xval + granularity) + " ms";
                    }
                }
            };
        },
        createGraph: function() {
            var data = this.data;
            var options = this.getOptions();
            prepareOptions(options, data);
            $.plot($("#flotResponseTimeDistribution"), prepareData(data.result.series, $("#choicesResponseTimeDistribution")), options);
        }

};

// Response time distribution
function refreshResponseTimeDistribution() {
    var infos = responseTimeDistributionInfos;
    prepareSeries(infos.data);
    if(infos.data.result.series.length == 0) {
        setEmptyGraph("#bodyResponseTimeDistribution");
        return;
    }
    if (isGraph($("#flotResponseTimeDistribution"))){
        infos.createGraph();
    }else{
        var choiceContainer = $("#choicesResponseTimeDistribution");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        $('#footerResponseTimeDistribution .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
};


var syntheticResponseTimeDistributionInfos = {
        data: {"result": {"minY": 7.0, "minX": 0.0, "ticks": [[0, "Requests having \nresponse time <= 500ms"], [1, "Requests having \nresponse time > 500ms and <= 1,500ms"], [2, "Requests having \nresponse time > 1,500ms"], [3, "Requests in error"]], "maxY": 133.0, "series": [{"data": [[0.0, 50.0]], "color": "#9ACD32", "isOverall": false, "label": "Requests having \nresponse time <= 500ms", "isController": false}, {"data": [[1.0, 133.0]], "color": "yellow", "isOverall": false, "label": "Requests having \nresponse time > 500ms and <= 1,500ms", "isController": false}, {"data": [], "color": "orange", "isOverall": false, "label": "Requests having \nresponse time > 1,500ms", "isController": false}, {"data": [[3.0, 7.0]], "color": "#FF6347", "isOverall": false, "label": "Requests in error", "isController": false}], "supportsControllersDiscrimination": false, "maxX": 3.0, "title": "Synthetic Response Times Distribution"}},
        getOptions: function() {
            return {
                legend: {
                    noColumns: 2,
                    show: true,
                    container: '#legendSyntheticResponseTimeDistribution'
                },
                xaxis:{
                    axisLabel: "Response times ranges",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                    tickLength:0,
                    min:-0.5,
                    max:3.5
                },
                yaxis: {
                    axisLabel: "Number of responses",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                bars : {
                    show: true,
                    align: "center",
                    barWidth: 0.25,
                    fill:.75
                },
                grid: {
                    hoverable: true // IMPORTANT! this is needed for tooltip to
                                    // work
                },
                tooltip: true,
                tooltipOpts: {
                    content: function(label, xval, yval, flotItem){
                        return yval + " " + label;
                    }
                }
            };
        },
        createGraph: function() {
            var data = this.data;
            var options = this.getOptions();
            prepareOptions(options, data);
            options.xaxis.ticks = data.result.ticks;
            $.plot($("#flotSyntheticResponseTimeDistribution"), prepareData(data.result.series, $("#choicesSyntheticResponseTimeDistribution")), options);
        }

};

// Response time distribution
function refreshSyntheticResponseTimeDistribution() {
    var infos = syntheticResponseTimeDistributionInfos;
    prepareSeries(infos.data, true);
    if (isGraph($("#flotSyntheticResponseTimeDistribution"))){
        infos.createGraph();
    }else{
        var choiceContainer = $("#choicesSyntheticResponseTimeDistribution");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        $('#footerSyntheticResponseTimeDistribution .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
};

var activeThreadsOverTimeInfos = {
        data: {"result": {"minY": 45.06315789473686, "minX": 1.72743384E12, "maxY": 45.06315789473686, "series": [{"data": [[1.72743384E12, 45.06315789473686]], "isOverall": false, "label": "conf3", "isController": false}], "supportsControllersDiscrimination": false, "granularity": 60000, "maxX": 1.72743384E12, "title": "Active Threads Over Time"}},
        getOptions: function() {
            return {
                series: {
                    stack: true,
                    lines: {
                        show: true,
                        fill: true
                    },
                    points: {
                        show: true
                    }
                },
                xaxis: {
                    mode: "time",
                    timeformat: getTimeFormat(this.data.result.granularity),
                    axisLabel: getElapsedTimeLabel(this.data.result.granularity),
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                yaxis: {
                    axisLabel: "Number of active threads",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20
                },
                legend: {
                    noColumns: 6,
                    show: true,
                    container: '#legendActiveThreadsOverTime'
                },
                grid: {
                    hoverable: true // IMPORTANT! this is needed for tooltip to
                                    // work
                },
                selection: {
                    mode: 'xy'
                },
                tooltip: true,
                tooltipOpts: {
                    content: "%s : At %x there were %y active threads"
                }
            };
        },
        createGraph: function() {
            var data = this.data;
            var dataset = prepareData(data.result.series, $("#choicesActiveThreadsOverTime"));
            var options = this.getOptions();
            prepareOptions(options, data);
            $.plot($("#flotActiveThreadsOverTime"), dataset, options);
            // setup overview
            $.plot($("#overviewActiveThreadsOverTime"), dataset, prepareOverviewOptions(options));
        }
};

// Active Threads Over Time
function refreshActiveThreadsOverTime(fixTimestamps) {
    var infos = activeThreadsOverTimeInfos;
    prepareSeries(infos.data);
    if(fixTimestamps) {
        fixTimeStamps(infos.data.result.series, 10800000);
    }
    if(isGraph($("#flotActiveThreadsOverTime"))) {
        infos.createGraph();
    }else{
        var choiceContainer = $("#choicesActiveThreadsOverTime");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        setGraphZoomable("#flotActiveThreadsOverTime", "#overviewActiveThreadsOverTime");
        $('#footerActiveThreadsOverTime .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
};

var timeVsThreadsInfos = {
        data: {"result": {"minY": 385.0, "minX": 1.0, "maxY": 1015.0, "series": [{"data": [[35.0, 385.0], [34.0, 787.4], [37.0, 401.4], [36.0, 392.0], [39.0, 415.5], [38.0, 407.0], [40.0, 436.0], [41.0, 778.25], [42.0, 448.12500000000006], [43.0, 457.12500000000006], [45.0, 495.12499999999994], [44.0, 529.0], [47.0, 500.0], [46.0, 683.0], [48.0, 493.94444444444446], [49.0, 518.8333333333333], [51.0, 513.9000000000001], [52.0, 546.0], [53.0, 617.6666666666666], [55.0, 760.0], [54.0, 724.0], [57.0, 691.2500000000001], [59.0, 622.5454545454546], [58.0, 598.6666666666666], [60.0, 635.75], [64.0, 634.0], [65.0, 680.6], [4.0, 957.3333333333334], [6.0, 949.5], [7.0, 897.0], [11.0, 891.75], [15.0, 898.0], [16.0, 943.0], [1.0, 1015.0], [18.0, 867.0], [22.0, 845.5], [25.0, 849.3333333333334], [26.0, 750.0], [29.0, 811.0]], "isOverall": false, "label": "HTTP Request", "isController": false}, {"data": [[45.06315789473686, 626.2421052631581]], "isOverall": false, "label": "HTTP Request-Aggregated", "isController": false}], "supportsControllersDiscrimination": true, "maxX": 65.0, "title": "Time VS Threads"}},
        getOptions: function() {
            return {
                series: {
                    lines: {
                        show: true
                    },
                    points: {
                        show: true
                    }
                },
                xaxis: {
                    axisLabel: "Number of active threads",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                yaxis: {
                    axisLabel: "Average response times in ms",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20
                },
                legend: { noColumns: 2,show: true, container: '#legendTimeVsThreads' },
                selection: {
                    mode: 'xy'
                },
                grid: {
                    hoverable: true // IMPORTANT! this is needed for tooltip to work
                },
                tooltip: true,
                tooltipOpts: {
                    content: "%s: At %x.2 active threads, Average response time was %y.2 ms"
                }
            };
        },
        createGraph: function() {
            var data = this.data;
            var dataset = prepareData(data.result.series, $("#choicesTimeVsThreads"));
            var options = this.getOptions();
            prepareOptions(options, data);
            $.plot($("#flotTimesVsThreads"), dataset, options);
            // setup overview
            $.plot($("#overviewTimesVsThreads"), dataset, prepareOverviewOptions(options));
        }
};

// Time vs threads
function refreshTimeVsThreads(){
    var infos = timeVsThreadsInfos;
    prepareSeries(infos.data);
    if(infos.data.result.series.length == 0) {
        setEmptyGraph("#bodyTimeVsThreads");
        return;
    }
    if(isGraph($("#flotTimesVsThreads"))){
        infos.createGraph();
    }else{
        var choiceContainer = $("#choicesTimeVsThreads");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        setGraphZoomable("#flotTimesVsThreads", "#overviewTimesVsThreads");
        $('#footerTimeVsThreads .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
};

var bytesThroughputOverTimeInfos = {
        data : {"result": {"minY": 497.1666666666667, "minX": 1.72743384E12, "maxY": 731.5, "series": [{"data": [[1.72743384E12, 731.5]], "isOverall": false, "label": "Bytes received per second", "isController": false}, {"data": [[1.72743384E12, 497.1666666666667]], "isOverall": false, "label": "Bytes sent per second", "isController": false}], "supportsControllersDiscrimination": false, "granularity": 60000, "maxX": 1.72743384E12, "title": "Bytes Throughput Over Time"}},
        getOptions : function(){
            return {
                series: {
                    lines: {
                        show: true
                    },
                    points: {
                        show: true
                    }
                },
                xaxis: {
                    mode: "time",
                    timeformat: getTimeFormat(this.data.result.granularity),
                    axisLabel: getElapsedTimeLabel(this.data.result.granularity) ,
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                yaxis: {
                    axisLabel: "Bytes / sec",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                legend: {
                    noColumns: 2,
                    show: true,
                    container: '#legendBytesThroughputOverTime'
                },
                selection: {
                    mode: "xy"
                },
                grid: {
                    hoverable: true // IMPORTANT! this is needed for tooltip to
                                    // work
                },
                tooltip: true,
                tooltipOpts: {
                    content: "%s at %x was %y"
                }
            };
        },
        createGraph : function() {
            var data = this.data;
            var dataset = prepareData(data.result.series, $("#choicesBytesThroughputOverTime"));
            var options = this.getOptions();
            prepareOptions(options, data);
            $.plot($("#flotBytesThroughputOverTime"), dataset, options);
            // setup overview
            $.plot($("#overviewBytesThroughputOverTime"), dataset, prepareOverviewOptions(options));
        }
};

// Bytes throughput Over Time
function refreshBytesThroughputOverTime(fixTimestamps) {
    var infos = bytesThroughputOverTimeInfos;
    prepareSeries(infos.data);
    if(fixTimestamps) {
        fixTimeStamps(infos.data.result.series, 10800000);
    }
    if(isGraph($("#flotBytesThroughputOverTime"))){
        infos.createGraph();
    }else{
        var choiceContainer = $("#choicesBytesThroughputOverTime");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        setGraphZoomable("#flotBytesThroughputOverTime", "#overviewBytesThroughputOverTime");
        $('#footerBytesThroughputOverTime .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
}

var responseTimesOverTimeInfos = {
        data: {"result": {"minY": 626.2421052631581, "minX": 1.72743384E12, "maxY": 626.2421052631581, "series": [{"data": [[1.72743384E12, 626.2421052631581]], "isOverall": false, "label": "HTTP Request", "isController": false}], "supportsControllersDiscrimination": true, "granularity": 60000, "maxX": 1.72743384E12, "title": "Response Time Over Time"}},
        getOptions: function(){
            return {
                series: {
                    lines: {
                        show: true
                    },
                    points: {
                        show: true
                    }
                },
                xaxis: {
                    mode: "time",
                    timeformat: getTimeFormat(this.data.result.granularity),
                    axisLabel: getElapsedTimeLabel(this.data.result.granularity),
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                yaxis: {
                    axisLabel: "Average response time in ms",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                legend: {
                    noColumns: 2,
                    show: true,
                    container: '#legendResponseTimesOverTime'
                },
                selection: {
                    mode: 'xy'
                },
                grid: {
                    hoverable: true // IMPORTANT! this is needed for tooltip to
                                    // work
                },
                tooltip: true,
                tooltipOpts: {
                    content: "%s : at %x Average response time was %y ms"
                }
            };
        },
        createGraph: function() {
            var data = this.data;
            var dataset = prepareData(data.result.series, $("#choicesResponseTimesOverTime"));
            var options = this.getOptions();
            prepareOptions(options, data);
            $.plot($("#flotResponseTimesOverTime"), dataset, options);
            // setup overview
            $.plot($("#overviewResponseTimesOverTime"), dataset, prepareOverviewOptions(options));
        }
};

// Response Times Over Time
function refreshResponseTimeOverTime(fixTimestamps) {
    var infos = responseTimesOverTimeInfos;
    prepareSeries(infos.data);
    if(infos.data.result.series.length == 0) {
        setEmptyGraph("#bodyResponseTimeOverTime");
        return;
    }
    if(fixTimestamps) {
        fixTimeStamps(infos.data.result.series, 10800000);
    }
    if(isGraph($("#flotResponseTimesOverTime"))){
        infos.createGraph();
    }else{
        var choiceContainer = $("#choicesResponseTimesOverTime");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        setGraphZoomable("#flotResponseTimesOverTime", "#overviewResponseTimesOverTime");
        $('#footerResponseTimesOverTime .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
};

var latenciesOverTimeInfos = {
        data: {"result": {"minY": 626.2368421052629, "minX": 1.72743384E12, "maxY": 626.2368421052629, "series": [{"data": [[1.72743384E12, 626.2368421052629]], "isOverall": false, "label": "HTTP Request", "isController": false}], "supportsControllersDiscrimination": true, "granularity": 60000, "maxX": 1.72743384E12, "title": "Latencies Over Time"}},
        getOptions: function() {
            return {
                series: {
                    lines: {
                        show: true
                    },
                    points: {
                        show: true
                    }
                },
                xaxis: {
                    mode: "time",
                    timeformat: getTimeFormat(this.data.result.granularity),
                    axisLabel: getElapsedTimeLabel(this.data.result.granularity),
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                yaxis: {
                    axisLabel: "Average response latencies in ms",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                legend: {
                    noColumns: 2,
                    show: true,
                    container: '#legendLatenciesOverTime'
                },
                selection: {
                    mode: 'xy'
                },
                grid: {
                    hoverable: true // IMPORTANT! this is needed for tooltip to
                                    // work
                },
                tooltip: true,
                tooltipOpts: {
                    content: "%s : at %x Average latency was %y ms"
                }
            };
        },
        createGraph: function () {
            var data = this.data;
            var dataset = prepareData(data.result.series, $("#choicesLatenciesOverTime"));
            var options = this.getOptions();
            prepareOptions(options, data);
            $.plot($("#flotLatenciesOverTime"), dataset, options);
            // setup overview
            $.plot($("#overviewLatenciesOverTime"), dataset, prepareOverviewOptions(options));
        }
};

// Latencies Over Time
function refreshLatenciesOverTime(fixTimestamps) {
    var infos = latenciesOverTimeInfos;
    prepareSeries(infos.data);
    if(infos.data.result.series.length == 0) {
        setEmptyGraph("#bodyLatenciesOverTime");
        return;
    }
    if(fixTimestamps) {
        fixTimeStamps(infos.data.result.series, 10800000);
    }
    if(isGraph($("#flotLatenciesOverTime"))) {
        infos.createGraph();
    }else {
        var choiceContainer = $("#choicesLatenciesOverTime");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        setGraphZoomable("#flotLatenciesOverTime", "#overviewLatenciesOverTime");
        $('#footerLatenciesOverTime .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
};

var connectTimeOverTimeInfos = {
        data: {"result": {"minY": 0.41578947368421054, "minX": 1.72743384E12, "maxY": 0.41578947368421054, "series": [{"data": [[1.72743384E12, 0.41578947368421054]], "isOverall": false, "label": "HTTP Request", "isController": false}], "supportsControllersDiscrimination": true, "granularity": 60000, "maxX": 1.72743384E12, "title": "Connect Time Over Time"}},
        getOptions: function() {
            return {
                series: {
                    lines: {
                        show: true
                    },
                    points: {
                        show: true
                    }
                },
                xaxis: {
                    mode: "time",
                    timeformat: getTimeFormat(this.data.result.granularity),
                    axisLabel: getConnectTimeLabel(this.data.result.granularity),
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                yaxis: {
                    axisLabel: "Average Connect Time in ms",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                legend: {
                    noColumns: 2,
                    show: true,
                    container: '#legendConnectTimeOverTime'
                },
                selection: {
                    mode: 'xy'
                },
                grid: {
                    hoverable: true // IMPORTANT! this is needed for tooltip to
                                    // work
                },
                tooltip: true,
                tooltipOpts: {
                    content: "%s : at %x Average connect time was %y ms"
                }
            };
        },
        createGraph: function () {
            var data = this.data;
            var dataset = prepareData(data.result.series, $("#choicesConnectTimeOverTime"));
            var options = this.getOptions();
            prepareOptions(options, data);
            $.plot($("#flotConnectTimeOverTime"), dataset, options);
            // setup overview
            $.plot($("#overviewConnectTimeOverTime"), dataset, prepareOverviewOptions(options));
        }
};

// Connect Time Over Time
function refreshConnectTimeOverTime(fixTimestamps) {
    var infos = connectTimeOverTimeInfos;
    prepareSeries(infos.data);
    if(infos.data.result.series.length == 0) {
        setEmptyGraph("#bodyConnectTimeOverTime");
        return;
    }
    if(fixTimestamps) {
        fixTimeStamps(infos.data.result.series, 10800000);
    }
    if(isGraph($("#flotConnectTimeOverTime"))) {
        infos.createGraph();
    }else {
        var choiceContainer = $("#choicesConnectTimeOverTime");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        setGraphZoomable("#flotConnectTimeOverTime", "#overviewConnectTimeOverTime");
        $('#footerConnectTimeOverTime .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
};

var responseTimePercentilesOverTimeInfos = {
        data: {"result": {"minY": 385.0, "minX": 1.72743384E12, "maxY": 914.0, "series": [{"data": [[1.72743384E12, 914.0]], "isOverall": false, "label": "Max", "isController": false}, {"data": [[1.72743384E12, 838.0]], "isOverall": false, "label": "90th percentile", "isController": false}, {"data": [[1.72743384E12, 912.3199999999999]], "isOverall": false, "label": "99th percentile", "isController": false}, {"data": [[1.72743384E12, 884.4]], "isOverall": false, "label": "95th percentile", "isController": false}, {"data": [[1.72743384E12, 385.0]], "isOverall": false, "label": "Min", "isController": false}, {"data": [[1.72743384E12, 580.0]], "isOverall": false, "label": "Median", "isController": false}], "supportsControllersDiscrimination": false, "granularity": 60000, "maxX": 1.72743384E12, "title": "Response Time Percentiles Over Time (successful requests only)"}},
        getOptions: function() {
            return {
                series: {
                    lines: {
                        show: true,
                        fill: true
                    },
                    points: {
                        show: true
                    }
                },
                xaxis: {
                    mode: "time",
                    timeformat: getTimeFormat(this.data.result.granularity),
                    axisLabel: getElapsedTimeLabel(this.data.result.granularity),
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                yaxis: {
                    axisLabel: "Response Time in ms",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                legend: {
                    noColumns: 2,
                    show: true,
                    container: '#legendResponseTimePercentilesOverTime'
                },
                selection: {
                    mode: 'xy'
                },
                grid: {
                    hoverable: true // IMPORTANT! this is needed for tooltip to
                                    // work
                },
                tooltip: true,
                tooltipOpts: {
                    content: "%s : at %x Response time was %y ms"
                }
            };
        },
        createGraph: function () {
            var data = this.data;
            var dataset = prepareData(data.result.series, $("#choicesResponseTimePercentilesOverTime"));
            var options = this.getOptions();
            prepareOptions(options, data);
            $.plot($("#flotResponseTimePercentilesOverTime"), dataset, options);
            // setup overview
            $.plot($("#overviewResponseTimePercentilesOverTime"), dataset, prepareOverviewOptions(options));
        }
};

// Response Time Percentiles Over Time
function refreshResponseTimePercentilesOverTime(fixTimestamps) {
    var infos = responseTimePercentilesOverTimeInfos;
    prepareSeries(infos.data);
    if(fixTimestamps) {
        fixTimeStamps(infos.data.result.series, 10800000);
    }
    if(isGraph($("#flotResponseTimePercentilesOverTime"))) {
        infos.createGraph();
    }else {
        var choiceContainer = $("#choicesResponseTimePercentilesOverTime");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        setGraphZoomable("#flotResponseTimePercentilesOverTime", "#overviewResponseTimePercentilesOverTime");
        $('#footerResponseTimePercentilesOverTime .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
};


var responseTimeVsRequestInfos = {
    data: {"result": {"minY": 409.0, "minX": 15.0, "maxY": 955.0, "series": [{"data": [[66.0, 711.0], [84.0, 512.0], [25.0, 883.5], [15.0, 409.0]], "isOverall": false, "label": "Successes", "isController": false}, {"data": [[25.0, 955.0]], "isOverall": false, "label": "Failures", "isController": false}], "supportsControllersDiscrimination": false, "granularity": 1000, "maxX": 84.0, "title": "Response Time Vs Request"}},
    getOptions: function() {
        return {
            series: {
                lines: {
                    show: false
                },
                points: {
                    show: true
                }
            },
            xaxis: {
                axisLabel: "Global number of requests per second",
                axisLabelUseCanvas: true,
                axisLabelFontSizePixels: 12,
                axisLabelFontFamily: 'Verdana, Arial',
                axisLabelPadding: 20,
            },
            yaxis: {
                axisLabel: "Median Response Time in ms",
                axisLabelUseCanvas: true,
                axisLabelFontSizePixels: 12,
                axisLabelFontFamily: 'Verdana, Arial',
                axisLabelPadding: 20,
            },
            legend: {
                noColumns: 2,
                show: true,
                container: '#legendResponseTimeVsRequest'
            },
            selection: {
                mode: 'xy'
            },
            grid: {
                hoverable: true // IMPORTANT! this is needed for tooltip to work
            },
            tooltip: true,
            tooltipOpts: {
                content: "%s : Median response time at %x req/s was %y ms"
            },
            colors: ["#9ACD32", "#FF6347"]
        };
    },
    createGraph: function () {
        var data = this.data;
        var dataset = prepareData(data.result.series, $("#choicesResponseTimeVsRequest"));
        var options = this.getOptions();
        prepareOptions(options, data);
        $.plot($("#flotResponseTimeVsRequest"), dataset, options);
        // setup overview
        $.plot($("#overviewResponseTimeVsRequest"), dataset, prepareOverviewOptions(options));

    }
};

// Response Time vs Request
function refreshResponseTimeVsRequest() {
    var infos = responseTimeVsRequestInfos;
    prepareSeries(infos.data);
    if (isGraph($("#flotResponseTimeVsRequest"))){
        infos.createGraph();
    }else{
        var choiceContainer = $("#choicesResponseTimeVsRequest");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        setGraphZoomable("#flotResponseTimeVsRequest", "#overviewResponseTimeVsRequest");
        $('#footerResponseRimeVsRequest .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
};


var latenciesVsRequestInfos = {
    data: {"result": {"minY": 409.0, "minX": 15.0, "maxY": 955.0, "series": [{"data": [[66.0, 711.0], [84.0, 512.0], [25.0, 883.5], [15.0, 409.0]], "isOverall": false, "label": "Successes", "isController": false}, {"data": [[25.0, 955.0]], "isOverall": false, "label": "Failures", "isController": false}], "supportsControllersDiscrimination": false, "granularity": 1000, "maxX": 84.0, "title": "Latencies Vs Request"}},
    getOptions: function() {
        return{
            series: {
                lines: {
                    show: false
                },
                points: {
                    show: true
                }
            },
            xaxis: {
                axisLabel: "Global number of requests per second",
                axisLabelUseCanvas: true,
                axisLabelFontSizePixels: 12,
                axisLabelFontFamily: 'Verdana, Arial',
                axisLabelPadding: 20,
            },
            yaxis: {
                axisLabel: "Median Latency in ms",
                axisLabelUseCanvas: true,
                axisLabelFontSizePixels: 12,
                axisLabelFontFamily: 'Verdana, Arial',
                axisLabelPadding: 20,
            },
            legend: { noColumns: 2,show: true, container: '#legendLatencyVsRequest' },
            selection: {
                mode: 'xy'
            },
            grid: {
                hoverable: true // IMPORTANT! this is needed for tooltip to work
            },
            tooltip: true,
            tooltipOpts: {
                content: "%s : Median Latency time at %x req/s was %y ms"
            },
            colors: ["#9ACD32", "#FF6347"]
        };
    },
    createGraph: function () {
        var data = this.data;
        var dataset = prepareData(data.result.series, $("#choicesLatencyVsRequest"));
        var options = this.getOptions();
        prepareOptions(options, data);
        $.plot($("#flotLatenciesVsRequest"), dataset, options);
        // setup overview
        $.plot($("#overviewLatenciesVsRequest"), dataset, prepareOverviewOptions(options));
    }
};

// Latencies vs Request
function refreshLatenciesVsRequest() {
        var infos = latenciesVsRequestInfos;
        prepareSeries(infos.data);
        if(isGraph($("#flotLatenciesVsRequest"))){
            infos.createGraph();
        }else{
            var choiceContainer = $("#choicesLatencyVsRequest");
            createLegend(choiceContainer, infos);
            infos.createGraph();
            setGraphZoomable("#flotLatenciesVsRequest", "#overviewLatenciesVsRequest");
            $('#footerLatenciesVsRequest .legendColorBox > div').each(function(i){
                $(this).clone().prependTo(choiceContainer.find("li").eq(i));
            });
        }
};

var hitsPerSecondInfos = {
        data: {"result": {"minY": 3.1666666666666665, "minX": 1.72743384E12, "maxY": 3.1666666666666665, "series": [{"data": [[1.72743384E12, 3.1666666666666665]], "isOverall": false, "label": "hitsPerSecond", "isController": false}], "supportsControllersDiscrimination": false, "granularity": 60000, "maxX": 1.72743384E12, "title": "Hits Per Second"}},
        getOptions: function() {
            return {
                series: {
                    lines: {
                        show: true
                    },
                    points: {
                        show: true
                    }
                },
                xaxis: {
                    mode: "time",
                    timeformat: getTimeFormat(this.data.result.granularity),
                    axisLabel: getElapsedTimeLabel(this.data.result.granularity),
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                yaxis: {
                    axisLabel: "Number of hits / sec",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20
                },
                legend: {
                    noColumns: 2,
                    show: true,
                    container: "#legendHitsPerSecond"
                },
                selection: {
                    mode : 'xy'
                },
                grid: {
                    hoverable: true // IMPORTANT! this is needed for tooltip to
                                    // work
                },
                tooltip: true,
                tooltipOpts: {
                    content: "%s at %x was %y.2 hits/sec"
                }
            };
        },
        createGraph: function createGraph() {
            var data = this.data;
            var dataset = prepareData(data.result.series, $("#choicesHitsPerSecond"));
            var options = this.getOptions();
            prepareOptions(options, data);
            $.plot($("#flotHitsPerSecond"), dataset, options);
            // setup overview
            $.plot($("#overviewHitsPerSecond"), dataset, prepareOverviewOptions(options));
        }
};

// Hits per second
function refreshHitsPerSecond(fixTimestamps) {
    var infos = hitsPerSecondInfos;
    prepareSeries(infos.data);
    if(fixTimestamps) {
        fixTimeStamps(infos.data.result.series, 10800000);
    }
    if (isGraph($("#flotHitsPerSecond"))){
        infos.createGraph();
    }else{
        var choiceContainer = $("#choicesHitsPerSecond");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        setGraphZoomable("#flotHitsPerSecond", "#overviewHitsPerSecond");
        $('#footerHitsPerSecond .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
}

var codesPerSecondInfos = {
        data: {"result": {"minY": 3.1666666666666665, "minX": 1.72743384E12, "maxY": 3.1666666666666665, "series": [{"data": [[1.72743384E12, 3.1666666666666665]], "isOverall": false, "label": "200", "isController": false}], "supportsControllersDiscrimination": false, "granularity": 60000, "maxX": 1.72743384E12, "title": "Codes Per Second"}},
        getOptions: function(){
            return {
                series: {
                    lines: {
                        show: true
                    },
                    points: {
                        show: true
                    }
                },
                xaxis: {
                    mode: "time",
                    timeformat: getTimeFormat(this.data.result.granularity),
                    axisLabel: getElapsedTimeLabel(this.data.result.granularity),
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                yaxis: {
                    axisLabel: "Number of responses / sec",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                legend: {
                    noColumns: 2,
                    show: true,
                    container: "#legendCodesPerSecond"
                },
                selection: {
                    mode: 'xy'
                },
                grid: {
                    hoverable: true // IMPORTANT! this is needed for tooltip to
                                    // work
                },
                tooltip: true,
                tooltipOpts: {
                    content: "Number of Response Codes %s at %x was %y.2 responses / sec"
                }
            };
        },
    createGraph: function() {
        var data = this.data;
        var dataset = prepareData(data.result.series, $("#choicesCodesPerSecond"));
        var options = this.getOptions();
        prepareOptions(options, data);
        $.plot($("#flotCodesPerSecond"), dataset, options);
        // setup overview
        $.plot($("#overviewCodesPerSecond"), dataset, prepareOverviewOptions(options));
    }
};

// Codes per second
function refreshCodesPerSecond(fixTimestamps) {
    var infos = codesPerSecondInfos;
    prepareSeries(infos.data);
    if(fixTimestamps) {
        fixTimeStamps(infos.data.result.series, 10800000);
    }
    if(isGraph($("#flotCodesPerSecond"))){
        infos.createGraph();
    }else{
        var choiceContainer = $("#choicesCodesPerSecond");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        setGraphZoomable("#flotCodesPerSecond", "#overviewCodesPerSecond");
        $('#footerCodesPerSecond .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
};

var transactionsPerSecondInfos = {
        data: {"result": {"minY": 0.11666666666666667, "minX": 1.72743384E12, "maxY": 3.05, "series": [{"data": [[1.72743384E12, 3.05]], "isOverall": false, "label": "HTTP Request-success", "isController": false}, {"data": [[1.72743384E12, 0.11666666666666667]], "isOverall": false, "label": "HTTP Request-failure", "isController": false}], "supportsControllersDiscrimination": true, "granularity": 60000, "maxX": 1.72743384E12, "title": "Transactions Per Second"}},
        getOptions: function(){
            return {
                series: {
                    lines: {
                        show: true
                    },
                    points: {
                        show: true
                    }
                },
                xaxis: {
                    mode: "time",
                    timeformat: getTimeFormat(this.data.result.granularity),
                    axisLabel: getElapsedTimeLabel(this.data.result.granularity),
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                yaxis: {
                    axisLabel: "Number of transactions / sec",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20
                },
                legend: {
                    noColumns: 2,
                    show: true,
                    container: "#legendTransactionsPerSecond"
                },
                selection: {
                    mode: 'xy'
                },
                grid: {
                    hoverable: true // IMPORTANT! this is needed for tooltip to
                                    // work
                },
                tooltip: true,
                tooltipOpts: {
                    content: "%s at %x was %y transactions / sec"
                }
            };
        },
    createGraph: function () {
        var data = this.data;
        var dataset = prepareData(data.result.series, $("#choicesTransactionsPerSecond"));
        var options = this.getOptions();
        prepareOptions(options, data);
        $.plot($("#flotTransactionsPerSecond"), dataset, options);
        // setup overview
        $.plot($("#overviewTransactionsPerSecond"), dataset, prepareOverviewOptions(options));
    }
};

// Transactions per second
function refreshTransactionsPerSecond(fixTimestamps) {
    var infos = transactionsPerSecondInfos;
    prepareSeries(infos.data);
    if(infos.data.result.series.length == 0) {
        setEmptyGraph("#bodyTransactionsPerSecond");
        return;
    }
    if(fixTimestamps) {
        fixTimeStamps(infos.data.result.series, 10800000);
    }
    if(isGraph($("#flotTransactionsPerSecond"))){
        infos.createGraph();
    }else{
        var choiceContainer = $("#choicesTransactionsPerSecond");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        setGraphZoomable("#flotTransactionsPerSecond", "#overviewTransactionsPerSecond");
        $('#footerTransactionsPerSecond .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
};

var totalTPSInfos = {
        data: {"result": {"minY": 0.11666666666666667, "minX": 1.72743384E12, "maxY": 3.05, "series": [{"data": [[1.72743384E12, 3.05]], "isOverall": false, "label": "Transaction-success", "isController": false}, {"data": [[1.72743384E12, 0.11666666666666667]], "isOverall": false, "label": "Transaction-failure", "isController": false}], "supportsControllersDiscrimination": true, "granularity": 60000, "maxX": 1.72743384E12, "title": "Total Transactions Per Second"}},
        getOptions: function(){
            return {
                series: {
                    lines: {
                        show: true
                    },
                    points: {
                        show: true
                    }
                },
                xaxis: {
                    mode: "time",
                    timeformat: getTimeFormat(this.data.result.granularity),
                    axisLabel: getElapsedTimeLabel(this.data.result.granularity),
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                yaxis: {
                    axisLabel: "Number of transactions / sec",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20
                },
                legend: {
                    noColumns: 2,
                    show: true,
                    container: "#legendTotalTPS"
                },
                selection: {
                    mode: 'xy'
                },
                grid: {
                    hoverable: true // IMPORTANT! this is needed for tooltip to
                                    // work
                },
                tooltip: true,
                tooltipOpts: {
                    content: "%s at %x was %y transactions / sec"
                },
                colors: ["#9ACD32", "#FF6347"]
            };
        },
    createGraph: function () {
        var data = this.data;
        var dataset = prepareData(data.result.series, $("#choicesTotalTPS"));
        var options = this.getOptions();
        prepareOptions(options, data);
        $.plot($("#flotTotalTPS"), dataset, options);
        // setup overview
        $.plot($("#overviewTotalTPS"), dataset, prepareOverviewOptions(options));
    }
};

// Total Transactions per second
function refreshTotalTPS(fixTimestamps) {
    var infos = totalTPSInfos;
    // We want to ignore seriesFilter
    prepareSeries(infos.data, false, true);
    if(fixTimestamps) {
        fixTimeStamps(infos.data.result.series, 10800000);
    }
    if(isGraph($("#flotTotalTPS"))){
        infos.createGraph();
    }else{
        var choiceContainer = $("#choicesTotalTPS");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        setGraphZoomable("#flotTotalTPS", "#overviewTotalTPS");
        $('#footerTotalTPS .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
};

// Collapse the graph matching the specified DOM element depending the collapsed
// status
function collapse(elem, collapsed){
    if(collapsed){
        $(elem).parent().find(".fa-chevron-up").removeClass("fa-chevron-up").addClass("fa-chevron-down");
    } else {
        $(elem).parent().find(".fa-chevron-down").removeClass("fa-chevron-down").addClass("fa-chevron-up");
        if (elem.id == "bodyBytesThroughputOverTime") {
            if (isGraph($(elem).find('.flot-chart-content')) == false) {
                refreshBytesThroughputOverTime(true);
            }
            document.location.href="#bytesThroughputOverTime";
        } else if (elem.id == "bodyLatenciesOverTime") {
            if (isGraph($(elem).find('.flot-chart-content')) == false) {
                refreshLatenciesOverTime(true);
            }
            document.location.href="#latenciesOverTime";
        } else if (elem.id == "bodyCustomGraph") {
            if (isGraph($(elem).find('.flot-chart-content')) == false) {
                refreshCustomGraph(true);
            }
            document.location.href="#responseCustomGraph";
        } else if (elem.id == "bodyConnectTimeOverTime") {
            if (isGraph($(elem).find('.flot-chart-content')) == false) {
                refreshConnectTimeOverTime(true);
            }
            document.location.href="#connectTimeOverTime";
        } else if (elem.id == "bodyResponseTimePercentilesOverTime") {
            if (isGraph($(elem).find('.flot-chart-content')) == false) {
                refreshResponseTimePercentilesOverTime(true);
            }
            document.location.href="#responseTimePercentilesOverTime";
        } else if (elem.id == "bodyResponseTimeDistribution") {
            if (isGraph($(elem).find('.flot-chart-content')) == false) {
                refreshResponseTimeDistribution();
            }
            document.location.href="#responseTimeDistribution" ;
        } else if (elem.id == "bodySyntheticResponseTimeDistribution") {
            if (isGraph($(elem).find('.flot-chart-content')) == false) {
                refreshSyntheticResponseTimeDistribution();
            }
            document.location.href="#syntheticResponseTimeDistribution" ;
        } else if (elem.id == "bodyActiveThreadsOverTime") {
            if (isGraph($(elem).find('.flot-chart-content')) == false) {
                refreshActiveThreadsOverTime(true);
            }
            document.location.href="#activeThreadsOverTime";
        } else if (elem.id == "bodyTimeVsThreads") {
            if (isGraph($(elem).find('.flot-chart-content')) == false) {
                refreshTimeVsThreads();
            }
            document.location.href="#timeVsThreads" ;
        } else if (elem.id == "bodyCodesPerSecond") {
            if (isGraph($(elem).find('.flot-chart-content')) == false) {
                refreshCodesPerSecond(true);
            }
            document.location.href="#codesPerSecond";
        } else if (elem.id == "bodyTransactionsPerSecond") {
            if (isGraph($(elem).find('.flot-chart-content')) == false) {
                refreshTransactionsPerSecond(true);
            }
            document.location.href="#transactionsPerSecond";
        } else if (elem.id == "bodyTotalTPS") {
            if (isGraph($(elem).find('.flot-chart-content')) == false) {
                refreshTotalTPS(true);
            }
            document.location.href="#totalTPS";
        } else if (elem.id == "bodyResponseTimeVsRequest") {
            if (isGraph($(elem).find('.flot-chart-content')) == false) {
                refreshResponseTimeVsRequest();
            }
            document.location.href="#responseTimeVsRequest";
        } else if (elem.id == "bodyLatenciesVsRequest") {
            if (isGraph($(elem).find('.flot-chart-content')) == false) {
                refreshLatenciesVsRequest();
            }
            document.location.href="#latencyVsRequest";
        }
    }
}

/*
 * Activates or deactivates all series of the specified graph (represented by id parameter)
 * depending on checked argument.
 */
function toggleAll(id, checked){
    var placeholder = document.getElementById(id);

    var cases = $(placeholder).find(':checkbox');
    cases.prop('checked', checked);
    $(cases).parent().children().children().toggleClass("legend-disabled", !checked);

    var choiceContainer;
    if ( id == "choicesBytesThroughputOverTime"){
        choiceContainer = $("#choicesBytesThroughputOverTime");
        refreshBytesThroughputOverTime(false);
    } else if(id == "choicesResponseTimesOverTime"){
        choiceContainer = $("#choicesResponseTimesOverTime");
        refreshResponseTimeOverTime(false);
    }else if(id == "choicesResponseCustomGraph"){
        choiceContainer = $("#choicesResponseCustomGraph");
        refreshCustomGraph(false);
    } else if ( id == "choicesLatenciesOverTime"){
        choiceContainer = $("#choicesLatenciesOverTime");
        refreshLatenciesOverTime(false);
    } else if ( id == "choicesConnectTimeOverTime"){
        choiceContainer = $("#choicesConnectTimeOverTime");
        refreshConnectTimeOverTime(false);
    } else if ( id == "choicesResponseTimePercentilesOverTime"){
        choiceContainer = $("#choicesResponseTimePercentilesOverTime");
        refreshResponseTimePercentilesOverTime(false);
    } else if ( id == "choicesResponseTimePercentiles"){
        choiceContainer = $("#choicesResponseTimePercentiles");
        refreshResponseTimePercentiles();
    } else if(id == "choicesActiveThreadsOverTime"){
        choiceContainer = $("#choicesActiveThreadsOverTime");
        refreshActiveThreadsOverTime(false);
    } else if ( id == "choicesTimeVsThreads"){
        choiceContainer = $("#choicesTimeVsThreads");
        refreshTimeVsThreads();
    } else if ( id == "choicesSyntheticResponseTimeDistribution"){
        choiceContainer = $("#choicesSyntheticResponseTimeDistribution");
        refreshSyntheticResponseTimeDistribution();
    } else if ( id == "choicesResponseTimeDistribution"){
        choiceContainer = $("#choicesResponseTimeDistribution");
        refreshResponseTimeDistribution();
    } else if ( id == "choicesHitsPerSecond"){
        choiceContainer = $("#choicesHitsPerSecond");
        refreshHitsPerSecond(false);
    } else if(id == "choicesCodesPerSecond"){
        choiceContainer = $("#choicesCodesPerSecond");
        refreshCodesPerSecond(false);
    } else if ( id == "choicesTransactionsPerSecond"){
        choiceContainer = $("#choicesTransactionsPerSecond");
        refreshTransactionsPerSecond(false);
    } else if ( id == "choicesTotalTPS"){
        choiceContainer = $("#choicesTotalTPS");
        refreshTotalTPS(false);
    } else if ( id == "choicesResponseTimeVsRequest"){
        choiceContainer = $("#choicesResponseTimeVsRequest");
        refreshResponseTimeVsRequest();
    } else if ( id == "choicesLatencyVsRequest"){
        choiceContainer = $("#choicesLatencyVsRequest");
        refreshLatenciesVsRequest();
    }
    var color = checked ? "black" : "#818181";
    if(choiceContainer != null) {
        choiceContainer.find("label").each(function(){
            this.style.color = color;
        });
    }
}

