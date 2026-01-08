let itemCount = 0;

// 페이지 로드시 기본 항목 1개 추가
window.onload = function() {
    addItem();
};

// KPI 항목 추가
function addItem() {
    itemCount++;
    const container = document.getElementById('kpiItems');
    
    const itemHtml = `
        <div class="kpi-item" id="item${itemCount}">
            <button type="button" class="remove-btn" onclick="removeItem(${itemCount})">삭제</button>
            <h3>항목 ${itemCount}</h3>
            
            <div class="form-group">
                <label>분야코드:</label>
                <select id="kpiFldCd${itemCount}" onchange="updateDtlOptions(${itemCount})">
                    <option value="P">P - 생산성</option>
                    <option value="Q">Q - 품질</option>
                    <option value="D">D - 납기</option>
                    <option value="C">C - 원가</option>
                </select>
            </div>
            
            <div class="form-group">
                <label>세부코드:</label>
                <select id="kpiDtlCd${itemCount}">
                    <option value="A">A - 가동률 증가</option>
                    <option value="B" selected>B - 생산량 증가</option>
                    <option value="C">C - 제조리드타임 단축</option>
                    <option value="D">D - 매출증가율</option>
                    <option value="E">E - 생산품목 수 증가</option>
                </select>
            </div>
            
            <div class="form-group">
                <label>항목명:</label>
                <input type="text" id="kpiDtlNm${itemCount}" value="일일 생산량 증가">
            </div>
            
            <div class="form-group">
                <label>단위:</label>
                <input type="text" id="unit${itemCount}" value="EA">
            </div>
            
            <div class="form-group">
                <label>처리주기:</label>
                <select id="period${itemCount}">
                    <option value="일별">일별</option>
                    <option value="월별">월별</option>
                </select>
            </div>
            
            <div class="form-group">
                <label>현재값(이전):</label>
                <input type="number" id="currentValue${itemCount}" value="150">
            </div>
            
            <div class="form-group">
                <label>목표값:</label>
                <input type="number" id="targetValue${itemCount}" value="170">
            </div>
            
            <div class="form-group">
                <label>실제값:</label>
                <input type="number" id="actualValue${itemCount}" value="165">
            </div>
        </div>
    `;
    
    container.insertAdjacentHTML('beforeend', itemHtml);
}

// KPI 항목 삭제
function removeItem(id) {
    const item = document.getElementById('item' + id);
    if (item) {
        item.remove();
    }
}

// 분야별 세부코드 옵션 변경
function updateDtlOptions(itemId) {
    const fldCd = document.getElementById('kpiFldCd' + itemId).value;
    const dtlSelect = document.getElementById('kpiDtlCd' + itemId);
    
    let options = '';
    
    if (fldCd === 'P') {
        options = `
            <option value="A">A - 가동률 증가</option>
            <option value="B" selected>B - 생산량 증가</option>
            <option value="C">C - 제조리드타임 단축</option>
            <option value="D">D - 매출증가율</option>
            <option value="E">E - 생산품목 수 증가</option>
        `;
    } else if (fldCd === 'Q') {
        options = `
            <option value="A" selected>A - 불량 감소</option>
            <option value="B">B - 불량률 감소</option>
            <option value="C">C - 품질비용 절감</option>
            <option value="D">D - 반품율</option>
            <option value="E">E - 클레임 건수 감소</option>
        `;
    } else if (fldCd === 'D') {
        options = `
            <option value="A" selected>A - 납기단축</option>
            <option value="C">C - 수주출하 리드타임</option>
            <option value="D">D - 납기시간 단축</option>
            <option value="G">G - 납기 준수율</option>
        `;
    } else if (fldCd === 'C') {
        options = `
            <option value="A" selected>A - 작업공수절감률</option>
            <option value="B">B - 재고비용</option>
            <option value="C">C - 자재로스율 감소</option>
            <option value="E">E - 작업공수 절감</option>
            <option value="F">F - 제품원가</option>
        `;
    }
    
    dtlSelect.innerHTML = options;
}

// KPI 전송
function sendKpi() {
    const companyName = document.getElementById('companyName').value;
    const certKey = document.getElementById('certKey').value;
    
    // 모든 KPI 항목 수집
    const kpiItems = [];
    const itemElements = document.querySelectorAll('.kpi-item');
    
    itemElements.forEach((item) => {
        const id = item.id.replace('item', '');
        
        kpiItems.push({
            companyName: companyName,
            certKey: certKey,
            kpiFldCd: document.getElementById('kpiFldCd' + id).value,
            kpiDtlCd: document.getElementById('kpiDtlCd' + id).value,
            kpiDtlNm: document.getElementById('kpiDtlNm' + id).value,
            unit: document.getElementById('unit' + id).value,
            period: document.getElementById('period' + id).value,
            currentValue: parseFloat(document.getElementById('currentValue' + id).value),
            targetValue: parseFloat(document.getElementById('targetValue' + id).value),
            actualValue: parseFloat(document.getElementById('actualValue' + id).value)
        });
    });
    
    const requestData = {
        companyName: companyName,
        kpiItems: kpiItems
    };
    
    console.log('전송 데이터:', requestData);
    
    // POST 요청
    fetch('/kpi', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(requestData)
    })
    .then(response => response.json())
    .then(result => {
        console.log('응답:', result);
        displayResult(result);
    })
    .catch(error => {
        document.getElementById('result').innerHTML = 
            '<p style="color:red;">에러: ' + error + '</p>';
    });
}

// 결과 표시
function displayResult(result) {
    let html = '<h3>전송 결과</h3>';
    html += '<p><strong>회사명:</strong> ' + result.companyName + '</p>';
    html += '<p><strong>전송 성공:</strong> ' + result.sendResult + '</p>';
    html += '<p><strong>메시지:</strong> ' + result.message + '</p>';
    
    html += '<h4>전송된 항목:</h4>';
    
    if (result.kpiItems) {
        result.kpiItems.forEach((item, index) => {
            html += `
                <div class="result-item">
                    <strong>항목 ${index + 1}:</strong> ${item.kpiDtlNm}<br>
                    분야: ${item.kpiFldCd} | 세부: ${item.kpiDtlCd}<br>
                    현재값: ${item.currentValue} → 실제값: ${item.actualValue} (목표: ${item.targetValue})
                </div>
            `;
        });
    }
    
    document.getElementById('result').innerHTML = html;
}