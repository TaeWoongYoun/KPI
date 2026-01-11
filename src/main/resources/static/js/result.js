    window.onload = function() {
        loadAll();
    };

    function loadAll() {
        loadLastResult();
        loadHistory();
    }

    function showTab(tab) {
        document.querySelectorAll('.tab').forEach(t => t.classList.remove('active'));

        if (tab === 'last') {
            document.getElementById('lastResult').style.display = 'block';
            document.getElementById('historyResult').style.display = 'none';
            document.querySelectorAll('.tab')[0].classList.add('active');
        } else {
            document.getElementById('lastResult').style.display = 'none';
            document.getElementById('historyResult').style.display = 'block';
            document.querySelectorAll('.tab')[1].classList.add('active');
        }
    }

    // 마지막 전송 결과
    function loadLastResult() {
        fetch('/kpi/result')
        .then(response => response.json())
        .then(result => {
            let statusClass = result.sendResult ? 'success' : 'fail';
            let statusText = result.sendResult ? '성공' : '실패';

            let html = '<h2 class="' + statusClass + '">전송 ' + statusText + '</h2>';
            html += '<p><strong>회사명:</strong> ' + (result.companyName || '-') + '</p>';
            html += '<p><strong>메시지:</strong> ' + result.message + '</p>';

            if (result.kpiItems && result.kpiItems.length > 0) {
                html += '<h3>전송된 항목 (' + result.kpiItems.length + '건)</h3>';

                result.kpiItems.forEach((item, index) => {
                    html += '<div class="record">';
                    html += '<h4>' + (index + 1) + '. ' + item.kpiDtlNm + '</h4>';

                    html += '<table>';
                    html += '<tr><th>분야</th><td>' + item.kpiFldCd + '</td><th>세부코드</th><td>' + item.kpiDtlCd + '</td></tr>';
                    html += '<tr><th>단위</th><td>' + item.unit + '</td><th>처리주기</th><td>' + item.period + '</td></tr>';
                    html += '<tr><th>이전값</th><td>' + item.previousValue + '</td><th>현재값</th><td>' + item.currentValue + '</td></tr>';
                    html += '</table>';

                    html += '<div style="margin-top: 10px;">';
                    html += '<span class="rate rate-achieve">실적 달성률: ' + item.achieveRate.toFixed(2) + '%</span>';
                    html += '<span class="rate rate-target">목표 달성률: ' + item.targetRate.toFixed(2) + '%</span>';
                    html += '</div>';

                    html += '</div>';
                });
            }

            document.getElementById('lastResult').innerHTML = html;
        })
        .catch(error => {
            document.getElementById('lastResult').innerHTML = '<p class="fail">에러: ' + error + '</p>';
        });
    }

    // DB 전송 기록
    function loadHistory() {
        fetch('/kpi/records')
        .then(response => response.json())
        .then(records => {
            if (records.length === 0) {
                document.getElementById('historyResult').innerHTML = '<p>저장된 기록이 없습니다.</p>';
                return;
            }

            let html = '<h2>전송 기록 (' + records.length + '건)</h2>';

            records.forEach((record, index) => {
                html += '<div class="record">';
                html += '<h4>' + (index + 1) + '. ' + record.kpiDtlNm + '</h4>';
                html += '<p class="time">전송일시: ' + record.createdAt + '</p>';

                html += '<table>';
                html += '<tr><th>회사명</th><td>' + record.companyName + '</td><th>분야</th><td>' + record.kpiFldCd + '</td></tr>';
                html += '<tr><th>세부코드</th><td>' + record.kpiDtlCd + '</td><th>단위</th><td>' + record.unit + '</td></tr>';
                html += '<tr><th>이전값</th><td>' + record.previousValue + '</td><th>현재값</th><td>' + record.currentValue + '</td></tr>';
                html += '</table>';

                html += '<div style="margin-top: 10px;">';
                html += '<span class="rate rate-achieve">실적 달성률: ' + record.achieveRate.toFixed(2) + '%</span>';
                html += '<span class="rate rate-target">목표 달성률: ' + record.targetRate.toFixed(2) + '%</span>';
                html += '</div>';

                html += '</div>';
            });

            document.getElementById('historyResult').innerHTML = html;
        })
        .catch(error => {
            document.getElementById('historyResult').innerHTML = '<p class="fail">에러: ' + error + '</p>';
        });
    }