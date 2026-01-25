import React, { useState } from 'react';

const BASE_URL_S1 = 'https://localhost:8081/api'; // 请根据实际后端地址调整
const BASE_URL_S2 = 'https://localhost:8082/api'

// 格式化 XML 字符串，利用 XSLTProcessor 进行格式化输出
function formatXML(xmlDoc) {
    const xslt = `<?xml version="1.0" encoding="UTF-8"?>
  <xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="xml" indent="yes"/>
    <xsl:template match="/">
      <xsl:copy-of select="."/>
    </xsl:template>
  </xsl:stylesheet>`;
    const parser = new DOMParser();
    const xsltDoc = parser.parseFromString(xslt, 'application/xml');
    const xsltProcessor = new XSLTProcessor();
    xsltProcessor.importStylesheet(xsltDoc);
    const resultDoc = xsltProcessor.transformToDocument(xmlDoc);
    const serializer = new XMLSerializer();
    return serializer.serializeToString(resultDoc);
}

const SpaceMarineTable = ({ xmlData }) => {
    // 将 XML 转换为 DOM
    const parser = new DOMParser();
    const xmlDoc = parser.parseFromString(xmlData, 'text/xml');

    // 检查是否是错误响应
    const errorElement = xmlDoc.getElementsByTagName('error')[0];
    if (errorElement) {
        const errorCode = errorElement.getElementsByTagName('code')[0]?.textContent;
        const errorMessage = errorElement.getElementsByTagName('message')[0]?.textContent;
        const errorTime = errorElement.getElementsByTagName('time')[0]?.textContent;

        return (
            <div style={{ color: 'red', border: '1px solid red', padding: '10px', marginTop: '20px' }}>
                <h3>Error</h3>
                <p><strong>code:</strong> {errorCode}</p>
                <p><strong>message:</strong> {errorMessage}</p>
                <p><strong>time:</strong> {errorTime}</p>
            </div>
        );
    }

    // 处理单个 Space Marine
    const spaceMarines = xmlDoc.getElementsByTagName('space_marine');

    // 构造表格行
    const rows = Array.from(spaceMarines).map((spaceMarine) => {
        const id = spaceMarine.getElementsByTagName('id')[0]?.textContent;
        const name = spaceMarine.getElementsByTagName('name')[0]?.textContent;
        const x = spaceMarine.getElementsByTagName('coordinates')[0]?.getElementsByTagName('x')[0]?.textContent;
        const y = spaceMarine.getElementsByTagName('coordinates')[0]?.getElementsByTagName('y')[0]?.textContent;
        const health = spaceMarine.getElementsByTagName('health')[0]?.textContent;
        const heartCount = spaceMarine.getElementsByTagName('heart_count')[0]?.textContent;
        const height = spaceMarine.getElementsByTagName('height')[0]?.textContent;
        const meleeWeapon = spaceMarine.getElementsByTagName('melee_weapon')[0]?.textContent;
        const chapterName = spaceMarine.getElementsByTagName('chapter')[0]?.getElementsByTagName('name')[0]?.textContent;
        const chapterWorld = spaceMarine.getElementsByTagName('chapter')[0]?.getElementsByTagName('world')[0]?.textContent;

        return (
            <tr key={id}>
                <td>{id}</td>
                <td>{name}</td>
                <td>{x}</td>
                <td>{y}</td>
                <td>{health}</td>
                <td>{heartCount}</td>
                <td>{height}</td>
                <td>{meleeWeapon}</td>
                <td>{chapterName}</td>
                <td>{chapterWorld}</td>
            </tr>
        );
    });

    return (
        <table style={{ width: '100%', border: '1px solid black', borderCollapse: 'collapse', textAlign: 'center' }}>
            <thead>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>X</th>
                <th>Y</th>
                <th>Health</th>
                <th>Heart Count</th>
                <th>Height</th>
                <th>Melee Weapon</th>
                <th>Chapter Name</th>
                <th>Chapter World</th>
            </tr>
            </thead>
            <tbody>
            {rows}
            </tbody>
        </table>
    );
};

const ResponseMessage = ({ xmlData }) => {
    if (!xmlData) return null;

    // 解析 XML
    const parser = new DOMParser();
    const xmlDoc = parser.parseFromString(xmlData, 'text/xml');

    // 处理成功消息
    const successElement = xmlDoc.getElementsByTagName('success')[0];
    if (successElement) {
        const code = successElement.getElementsByTagName('code')[0]?.textContent;
        const message = successElement.getElementsByTagName('message')[0]?.textContent;
        const time = successElement.getElementsByTagName('time')[0]?.textContent;

        return (
            <div style={{ color: 'green', border: '1px solid green', padding: '10px', marginTop: '10px' }}>
                <h3>Success</h3>
                <p><strong>code:</strong> {code}</p>
                <p><strong>message:</strong> {message}</p>
                <p><strong>time:</strong> {time}</p>
            </div>
        );
    }

    // 处理错误消息
    const errorElement = xmlDoc.getElementsByTagName('error')[0];
    if (errorElement) {
        const code = errorElement.getElementsByTagName('code')[0]?.textContent;
        const message = errorElement.getElementsByTagName('message')[0]?.textContent;
        const time = errorElement.getElementsByTagName('time')[0]?.textContent;

        return (
            <div style={{ color: 'red', border: '1px solid red', padding: '10px', marginTop: '10px' }}>
                <h3>Error</h3>
                <p><strong>code:</strong> {code}</p>
                <p><strong>message:</strong> {message}</p>
                <p><strong>time:</strong> {time}</p>
            </div>
        );
    }

    // 如果没有识别的 XML 结构
    return <div style={{ color: 'gray', marginTop: '10px' }}>unrecognized response</div>;
};



export function Service1() {
    // GET /space-marine 列表接口状态
    const [listParams, setListParams] = useState({
        sort: '',
        order: 'ASC',
        filter: '',
        page: '0',
        pageSize: '10',
    });
    const [listResponse, setListResponse] = useState('');

    // GET /space-marine/{id} 状态
    const [byId, setById] = useState('');
    const [byIdResponse, setByIdResponse] = useState('');

    // POST /space-marine 状态
    const [postForm, setPostForm] = useState({
        name: '',
        x: '',
        y: '',
        health: '',
        heartCount: '',
        height: '',
        meleeWeapon: 'CHAIN_SWORD',
        chapterName: '',
        chapterWorld: '',
    });
    const [postResponse, setPostResponse] = useState('');

    // PUT /space-marine/{id} 状态
    const [putId, setPutId] = useState('');
    const [putResponse, setPutResponse] = useState('');

    // DELETE /space-marine/{id} 状态
    const [deleteId, setDeleteId] = useState('');
    const [deleteResponse, setDeleteResponse] = useState('');

    // DELETE /space-marine/by-heart-count/ 状态
    const [deleteHeartCount, setDeleteHeartCount] = useState('');
    const [deleteHeartCountResponse, setDeleteHeartCountResponse] = useState('');

    // GET /space-marine/count/by-melee-weapon/ 状态
    const [meleeWeapon, setMeleeWeapon] = useState('CHAIN_SWORD');
    const [countResponse, setCountResponse] = useState('');

    // GET /space-marine/by-name 状态
    const [namePrefix, setNamePrefix] = useState('');
    const [namePrefixResponse, setNamePrefixResponse] = useState('');

    // Starship操作状态
    const [unloadParams, setUnloadParams] = useState({
        starshipId: '',
        spaceMarineIds: ''
    });
    const [unloadResponse, setUnloadResponse] = useState('');

    const [unloadAllStarshipId, setUnloadAllStarshipId] = useState('');
    const [unloadAllResponse, setUnloadAllResponse] = useState('');

    // 处理 GET 列表参数变化
    const handleListChange = (e) => {
        setListParams({ ...listParams, [e.target.name]: e.target.value });
    };

    // 处理 GET 列表表单提交
    const handleListSubmit = (e) => {
        e.preventDefault();
        let url = `${BASE_URL_S1}/v1/space-marine?&order=${encodeURIComponent(listParams.order)}&page=${encodeURIComponent(
            listParams.page
        )}&page_size=${encodeURIComponent(listParams.pageSize)}`;

        if (listParams.sort.trim() !== '') {
            listParams.sort.split(',').forEach((f) => {
                url += `&sort=${encodeURIComponent(f.trim())}`;
            })
        }
        if (listParams.filter.trim() !== '') {
            listParams.filter.split(',').forEach((f) => {
                url += `&filter=${encodeURIComponent(f.trim())}`;
            });
        }


        fetch(url, { method: 'GET', mode: 'cors' })
            .then((res) => res.text())
            .then((data) => {
                const parser = new DOMParser();
                const xmlDoc = parser.parseFromString(data, 'text/xml');
                setListResponse(formatXML(xmlDoc));
            })
            .catch((err) => setListResponse('错误：' + err));
    };

    // 处理 GET by ID 表单提交
    const handleByIdSubmit = (e) => {
        e.preventDefault();
        const url = `${BASE_URL_S1}/v1/space-marine/${encodeURIComponent(byId)}`;
        fetch(url, { method: 'GET', mode: 'cors' })
            .then((res) => res.text())
            .then((data) => {
                const parser = new DOMParser();
                const xmlDoc = parser.parseFromString(data, 'text/xml');
                setByIdResponse(formatXML(xmlDoc));
            })
            .catch((err) => setByIdResponse('错误：' + err));
    };

    // 处理 POST 表单变化
    const handlePostChange = (e) => {
        setPostForm({ ...postForm, [e.target.name]: e.target.value });
    };

    // 处理 POST 表单提交
    const handlePostSubmit = (e) => {
        e.preventDefault();
        const xml = `<space_marine>
  <name>${postForm.name}</name>
  <coordinates>
    <x>${postForm.x}</x>
    <y>${postForm.y}</y>
  </coordinates>
  <health>${postForm.health}</health>
  <heart_count>${postForm.heartCount}</heart_count>
  <height>${postForm.height}</height>
  <melee_weapon>${postForm.meleeWeapon}</melee_weapon>
  <chapter>
    <name>${postForm.chapterName}</name>
    <world>${postForm.chapterWorld}</world>
  </chapter>
</space_marine>`;
        const url = `${BASE_URL_S1}/v1/space-marine`;
        fetch(url, {
            method: 'POST',
            mode: 'cors',
            headers: { 'Content-Type': 'application/xml' },
            body: xml,
        })
            .then((res) => res.text())
            .then((data) => {
                const parser = new DOMParser();
                const xmlDoc = parser.parseFromString(data, 'text/xml');
                setPostResponse(formatXML(xmlDoc));
            })
            .catch((err) => setPostResponse('错误：' + err));
    };

    // 处理PUT更新
    const handlePutSubmit = (e) => {
        e.preventDefault();
        const xml = `<space_marine>
  <name>${postForm.name}</name>
  <coordinates>
    <x>${postForm.x}</x>
    <y>${postForm.y}</y>
  </coordinates>
  <health>${postForm.health}</health>
  <heart_count>${postForm.heartCount}</heart_count>
  <height>${postForm.height}</height>
  <melee_weapon>${postForm.meleeWeapon}</melee_weapon>
  <chapter>
    <name>${postForm.chapterName}</name>
    <world>${postForm.chapterWorld}</world>
  </chapter>
</space_marine>`;
        const url = `${BASE_URL_S1}/v1/space-marine/${encodeURIComponent(putId)}`;
        fetch(url, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/xml' },
            body: xml,
        })
            .then(res => res.text())
            .then(data => {
                const parser = new DOMParser();
                const xmlDoc = parser.parseFromString(data, 'text/xml');
                setPutResponse(formatXML(xmlDoc));
            })
            .catch(err => setPutResponse('错误：' + err));
    };

    // 处理DELETE by ID
    const handleDeleteSubmit = (e) => {
        e.preventDefault();
        const url = `${BASE_URL_S1}/v1/space-marine/${encodeURIComponent(deleteId)}`;
        fetch(url, { method: 'DELETE' })
            .then(res => res.text())
            .then(data => {
                const parser = new DOMParser();
                const xmlDoc = parser.parseFromString(data, 'text/xml');
                setDeleteResponse(formatXML(xmlDoc));
            })
            .catch(err => setDeleteResponse('错误：' + err));
    };

    // 处理DELETE by heartCount
    const handleDeleteHeartCountSubmit = (e) => {
        e.preventDefault();
        const url = `${BASE_URL_S1}/v1/space-marine/by-heart-count/?heart_count=${encodeURIComponent(deleteHeartCount)}`;
        fetch(url, { method: 'DELETE' })
            .then(res => res.text())
            .then(data => {
                const parser = new DOMParser();
                const xmlDoc = parser.parseFromString(data, 'text/xml');
                setDeleteHeartCountResponse(formatXML(xmlDoc));
            })
            .catch(err => setDeleteHeartCountResponse('错误：' + err));
    };

    // 处理GET count
    const handleCountSubmit = (e) => {
        e.preventDefault();
        const url = `${BASE_URL_S1}/v1/space-marine/count/by-melee-weapon/?melee_weapon=${encodeURIComponent(meleeWeapon)}`;
        fetch(url)
            .then(res => res.text())
            .then(data => {
                const parser = new DOMParser();
                const xmlDoc = parser.parseFromString(data, 'text/xml');
                setCountResponse(formatXML(xmlDoc));
            })
            .catch(err => setCountResponse('错误：' + err));
    };

    // 处理GET by name prefix
    const handleNamePrefixSubmit = (e) => {
        e.preventDefault();
        const url = `${BASE_URL_S1}/v1/space-marine/by-name?name_prefix=${encodeURIComponent(namePrefix)}`;
        fetch(url)
            .then(res => res.text())
            .then(data => {
                const parser = new DOMParser();
                const xmlDoc = parser.parseFromString(data, 'text/xml');
                setNamePrefixResponse(formatXML(xmlDoc));
            })
            .catch(err => setNamePrefixResponse('错误：' + err));
    };

    // 在事件处理部分添加以下方法
    // 处理Starship卸载操作
    const handleUnloadSubmit = (e) => {
        e.preventDefault();
        const baseUrl = `${BASE_URL_S2}/v1/starship/${encodeURIComponent(unloadParams.starshipId)}/unload/space-marine-id`;
        const queryParams = unloadParams.spaceMarineIds.split(',')
            .map(id => `space_marine_id=${encodeURIComponent(id.trim())}`)
            .join('&');

        const url = `${baseUrl}${queryParams ? '?' + queryParams : ''}`;

        fetch(url, { method: 'GET' })
            .then(res => res.text())
            .then(data => {
                const parser = new DOMParser();
                const xmlDoc = parser.parseFromString(data, 'text/xml');
                setUnloadResponse(formatXML(xmlDoc));
            })
            .catch(err => setUnloadResponse('错误：' + err));
    };

    // 处理全部卸载操作
    const handleUnloadAllSubmit = (e) => {
        e.preventDefault();
        const url = `${BASE_URL_S2}/v1/starship/${encodeURIComponent(unloadAllStarshipId)}/unload-all`;

        fetch(url, { method: 'GET' })
            .then(res => res.text())
            .then(data => {
                const parser = new DOMParser();
                const xmlDoc = parser.parseFromString(data, 'text/xml');
                setUnloadAllResponse(formatXML(xmlDoc));
            })
            .catch(err => setUnloadAllResponse('错误：' + err));
    };

    // 内联样式
    const styles = {
        container: { fontFamily: 'Arial, sans-serif', margin: '20px' },
        section: { marginBottom: '20px', padding: '10px', border: '1px solid #ccc' },
        label: { display: 'block', marginTop: '8px' },
        input: { width: '100%', padding: '4px', marginTop: '4px' },
        response: { background: '#f7f7f7', border: '1px dashed #aaa', padding: '10px', whiteSpace: 'pre-wrap' },
        hr: { margin: '20px 0' },
        fieldset: { marginTop: '10px', padding: '10px' },
    };

    return (
        <div style={styles.container}>
            <h1>SpaceMarine API Test Page</h1>
            {/* GET /space-marine 列表接口 */}
            <section style={styles.section}>
                <h2>Get SpaceMarine List (GET /space-marine)</h2>
                <form onSubmit={handleListSubmit}>
                    <label style={styles.label}>
                        sort
                        <input
                            type="text"
                            name="sort"
                            value={listParams.sort}
                            onChange={handleListChange}
                            placeholder="例如：id, name"
                            style={styles.input}
                        />
                    </label>
                    <label style={styles.label}>
                        order (order):
                        <select name="order" value={listParams.order} onChange={handleListChange} style={styles.input}>
                            <option value="ASC">ASC</option>
                            <option value="DESC">DESC</option>
                        </select>
                    </label>
                    <label style={styles.label}>
                        filter (filter, split by comma, e.g. id&gt;10,name='Mike'):
                        <input
                            type="text"
                            name="filter"
                            value={listParams.filter}
                            onChange={handleListChange}
                            placeholder="例如：id&gt;10"
                            style={styles.input}
                        />
                    </label>
                    <label style={styles.label}>
                        page (page):
                        <input type="number" name="page" value={listParams.page} onChange={handleListChange} min="0" style={styles.input} />
                    </label>
                    <label style={styles.label}>
                        pageSize (pageSize):
                        <input type="number" name="pageSize" value={listParams.pageSize} onChange={handleListChange} min="1" style={styles.input} />
                    </label>
                    <button type="submit">Send GET request</button>
                </form>
                <div style={{marginTop: '20px'}}>
                    {listResponse && <SpaceMarineTable xmlData={listResponse}/>}
                </div>
            </section>

            <hr style={styles.hr}/>

            {/* GET /space-marine/{id} 接口 */}
            <section style={styles.section}>
                <h2>Get SpaceMarine by ID (GET /space-marine/id)</h2>
                <form onSubmit={handleByIdSubmit}>
                    <label style={styles.label}>
                        SpaceMarine ID:
                        <input
                            type="number"
                            value={byId}
                            onChange={(e) => setById(e.target.value)}
                            placeholder="输入 id，例如：1"
                            min="1"
                            style={styles.input}
                        />
                    </label>
                    <button type="submit">Send GET request</button>
                </form>
                <div style={{marginTop: '20px'}}>
                    {listResponse && <SpaceMarineTable xmlData={byIdResponse}/>}
                </div>
            </section>

            <hr style={styles.hr}/>

            {/* POST /space-marine 接口 */}
            <section style={styles.section}>
                <h2>Add SpaceMarine (POST /space-marine)</h2>
                <form onSubmit={handlePostSubmit}>
                    <label style={styles.label}>
                        Name:
                        <input type="text" name="name" value={postForm.name} onChange={handlePostChange} required style={styles.input} />
                    </label>
                    <fieldset style={styles.fieldset}>
                        <legend>Coordinates</legend>
                        <label style={styles.label}>
                            X (&lt=220):
                            <input type="number" name="x" value={postForm.x} onChange={handlePostChange} required style={styles.input} />
                        </label>
                        <label style={styles.label}>
                            Y (&lt=288):
                            <input type="number" name="y" value={postForm.y} onChange={handlePostChange} step="any" required style={styles.input} />
                        </label>
                    </fieldset>
                    <label style={styles.label}>
                        Health (>=1):
                        <input type="number" name="health" value={postForm.health} onChange={handlePostChange} required min="1" style={styles.input} />
                    </label>
                    <label style={styles.label}>
                        Heart Count (1 ~ 3):
                        <input type="number" name="heartCount" value={postForm.heartCount} onChange={handlePostChange} required min="1" max="3" style={styles.input} />
                    </label>
                    <label style={styles.label}>
                        Height:
                        <input type="number" name="height" value={postForm.height} onChange={handlePostChange} step="any" required style={styles.input} />
                    </label>
                    <label style={styles.label}>
                        Melee Weapon:
                        <select name="meleeWeapon" value={postForm.meleeWeapon} onChange={handlePostChange} style={styles.input}>
                            <option value="CHAIN_SWORD">CHAIN_SWORD</option>
                            <option value="LIGHTING_CLAW">LIGHTING_CLAW</option>
                            <option value="POWER_BLADE">POWER_BLADE</option>
                        </select>
                    </label>
                    <fieldset style={styles.fieldset}>
                        <legend>Chapter (Optional)</legend>
                        <label style={styles.label}>
                            Chapter Name:
                            <input type="text" name="chapterName" value={postForm.chapterName} onChange={handlePostChange} style={styles.input} />
                        </label>
                        <label style={styles.label}>
                            Chapter World:
                            <input type="text" name="chapterWorld" value={postForm.chapterWorld} onChange={handlePostChange} style={styles.input} />
                        </label>
                    </fieldset>
                    <button type="submit">Send POST request</button>
                </form>
                <div style={{marginTop: '20px'}}>
                    {listResponse && <SpaceMarineTable xmlData={postResponse}/>}
                </div>


            </section>

            {/* 新增PUT更新接口 */}
            <hr style={styles.hr}/>
            <section style={styles.section}>
                <h2>Update SpaceMarine (PUT /space-marine/id)</h2>
                <form onSubmit={handlePutSubmit}>
                    <label style={styles.label}>
                        ID to Update:
                        <input
                            type="number"
                            value={putId}
                            onChange={(e) => setPutId(e.target.value)}
                            min="1"
                            style={styles.input}
                            required
                        />
                    </label>
                    {/* 复用POST表单字段 */}
                    {Object.entries(postForm).map(([key, value]) => (
                        key !== 'chapterName' && key !== 'chapterWorld' && (
                            <label key={key} style={styles.label}>
                                {key.charAt(0).toUpperCase() + key.slice(1)}:
                                <input
                                    type={key === 'height' ? 'number' : 'text'}
                                    name={key}
                                    value={value}
                                    onChange={handlePostChange}
                                    style={styles.input}
                                    required={key !== 'meleeWeapon'}
                                />
                            </label>
                        )
                    ))}
                    <button type="submit">Send PUT request</button>
                </form>
                <div style={styles.response}>
                    <ResponseMessage xmlData={putResponse}/>
                </div>

            </section>

            {/* 新增DELETE接口 */}
            <hr style={styles.hr} />
            <section style={styles.section}>
                <h2>Delete Operations</h2>

                <div style={{ marginBottom: '20px' }}>
                    <h3>Delete by ID (DELETE /space-marine/id)</h3>
                    <form onSubmit={handleDeleteSubmit}>
                        <label style={styles.label}>
                            ID to Delete:
                            <input
                                type="number"
                                value={deleteId}
                                onChange={(e) => setDeleteId(e.target.value)}
                                min="1"
                                style={styles.input}
                                required
                            />
                        </label>
                        <button type="submit">Delete by ID</button>
                    </form>
                    <div style={styles.response}>
                        <ResponseMessage xmlData={deleteResponse}/>
                    </div>
                </div>

                <div>
                    <h3>Delete by Heart Count (DELETE /space-marine/by-heart-count/)</h3>
                    <form onSubmit={handleDeleteHeartCountSubmit}>
                        <label style={styles.label}>
                            Heart Count (&gt;0):
                            <input
                                type="number"
                                value={deleteHeartCount}
                                onChange={(e) => setDeleteHeartCount(e.target.value)}
                                min="1"
                                style={styles.input}
                                required
                            />
                        </label>
                        <button type="submit">Delete by Heart Count</button>
                    </form>
                    <div style={styles.response}>
                        <ResponseMessage xmlData={deleteHeartCountResponse}/>
                    </div>
                </div>
            </section>

            {/* 新增统计查询接口 */}
            <hr style={styles.hr}/>
            <section style={styles.section}>
                <h2>Query Operations</h2>

                <div style={{ marginBottom: '20px' }}>
                    <h3>Count by Melee Weapon (GET /space-marine/count/by-melee-weapon/)</h3>
                    <form onSubmit={handleCountSubmit}>
                        <label style={styles.label}>
                            Select Weapon:
                            <select
                                value={meleeWeapon}
                                onChange={(e) => setMeleeWeapon(e.target.value)}
                                style={styles.input}
                            >
                                <option value="CHAIN_SWORD">CHAIN_SWORD</option>
                                <option value="LIGHTING_CLAW">LIGHTING_CLAW</option>
                                <option value="POWER_BLADE">POWER_BLADE</option>
                            </select>
                        </label>
                        <button type="submit">Get Count</button>
                    </form>
                    <div style={styles.response}>
                        <ResponseMessage xmlData={countResponse}/>
                    </div>
                </div>

                <div>
                    <h3>Search by Name Prefix (GET /space-marine/by-name)</h3>
                    <form onSubmit={handleNamePrefixSubmit}>
                        <label style={styles.label}>
                            Name Prefix:
                            <input
                                type="text"
                                value={namePrefix}
                                onChange={(e) => setNamePrefix(e.target.value)}
                                style={styles.input}
                                required
                            />
                        </label>
                        <button type="submit">Search</button>
                    </form>
                    <div style={{marginTop: '20px'}}>
                        {listResponse && <SpaceMarineTable xmlData={namePrefixResponse}/>}
                    </div>
                </div>
            </section>

            <hr style={styles.hr}/>
            <section style={styles.section}>
            <h2>Starship Operations</h2>

                <div style={{ marginBottom: '20px' }}>
                    <h3>Unload SpaceMarine (GET /starship/&#123;id&#125;/unload/space-marine-id/)</h3>
                    <form onSubmit={handleUnloadSubmit}>
                        <label style={styles.label}>
                            Starship ID:
                            <input
                                type="number"
                                value={unloadParams.starshipId}
                                onChange={(e) => setUnloadParams(p => ({...p, starshipId: e.target.value}))}
                                min="1"
                                style={styles.input}
                                required
                            />
                        </label>
                        <label style={styles.label}>
                            SpaceMarine IDs (逗号分隔):
                            <input
                                type="text"
                                value={unloadParams.spaceMarineIds}
                                onChange={(e) => setUnloadParams(p => ({...p, spaceMarineIds: e.target.value}))}
                                placeholder="例如：1,2,3"
                                style={styles.input}
                                required
                            />
                        </label>
                        <button type="submit">Unload</button>
                    </form>
                    <div style={styles.response}>
                        <ResponseMessage xmlData={unloadResponse}/>
                    </div>
                </div>

                <div>
                    <h3>Unload All SpaceMarines (GET /starship/&#123;id&#125;/unload-all)</h3>
                    <form onSubmit={handleUnloadAllSubmit}>
                        <label style={styles.label}>
                            Starship ID:
                            <input
                                type="number"
                                value={unloadAllStarshipId}
                                onChange={(e) => setUnloadAllStarshipId(e.target.value)}
                                min="1"
                                style={styles.input}
                                required
                            />
                        </label>
                        <button type="submit">Unload all</button>
                    </form>
                    <div style={styles.response}>
                        <ResponseMessage xmlData={unloadAllResponse}/>
                    </div>
                </div>
            </section>
        </div>
    );
}
