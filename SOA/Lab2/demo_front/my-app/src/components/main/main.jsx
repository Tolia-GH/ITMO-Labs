import React, { useState } from 'react';

const BASE_URL = 'https://localhost:8181/api'; // 请根据实际后端地址调整

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

export function Main() {
    // GET /space-marine 列表接口状态
    const [listParams, setListParams] = useState({
        sort: 'id',
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

    // 处理 GET 列表参数变化
    const handleListChange = (e) => {
        setListParams({ ...listParams, [e.target.name]: e.target.value });
    };

    // 处理 GET 列表表单提交
    const handleListSubmit = (e) => {
        e.preventDefault();
        let url = `${BASE_URL}/v1/space-marine?sort=${encodeURIComponent(
            listParams.sort
        )}&order=${encodeURIComponent(listParams.order)}&page=${encodeURIComponent(
            listParams.page
        )}&pageSize=${encodeURIComponent(listParams.pageSize)}`;
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
        const url = `${BASE_URL}/v1/space-marine/${encodeURIComponent(byId)}`;
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
        const xml = `<newSpaceMarine>
  <name>${postForm.name}</name>
  <coordinates>
    <x>${postForm.x}</x>
    <y>${postForm.y}</y>
  </coordinates>
  <health>${postForm.health}</health>
  <heartCount>${postForm.heartCount}</heartCount>
  <height>${postForm.height}</height>
  <meleeWeapon>${postForm.meleeWeapon}</meleeWeapon>
  <chapter>
    <name>${postForm.chapterName}</name>
    <world>${postForm.chapterWorld}</world>
  </chapter>
</newSpaceMarine>`;
        const url = `${BASE_URL}/v1/space-marine`;
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
        const xml = `<newSpaceMarine>
  <name>${postForm.name}</name>
  <coordinates>
    <x>${postForm.x}</x>
    <y>${postForm.y}</y>
  </coordinates>
  <health>${postForm.health}</health>
  <heartCount>${postForm.heartCount}</heartCount>
  <height>${postForm.height}</height>
  <meleeWeapon>${postForm.meleeWeapon}</meleeWeapon>
  <chapter>
    <name>${postForm.chapterName}</name>
    <world>${postForm.chapterWorld}</world>
  </chapter>
</newSpaceMarine>`;
        const url = `${BASE_URL}/v1/space-marine/${encodeURIComponent(putId)}`;
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
        const url = `${BASE_URL}/v1/space-marine/${encodeURIComponent(deleteId)}`;
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
        const url = `${BASE_URL}/v1/space-marine/by-heart-count/?heartCount=${encodeURIComponent(deleteHeartCount)}`;
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
        const url = `${BASE_URL}/v1/space-marine/count/by-melee-weapon/?meleeWeapon=${encodeURIComponent(meleeWeapon)}`;
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
        const url = `${BASE_URL}/v1/space-marine/by-name?prefix=${encodeURIComponent(namePrefix)}`;
        fetch(url)
            .then(res => res.text())
            .then(data => {
                const parser = new DOMParser();
                const xmlDoc = parser.parseFromString(data, 'text/xml');
                setNamePrefixResponse(formatXML(xmlDoc));
            })
            .catch(err => setNamePrefixResponse('错误：' + err));
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
                        (sort):
                        <select name="sort" value={listParams.sort} onChange={handleListChange} style={styles.input}>
                            <option value="id">id</option>
                            <option value="name">name</option>
                            <option value="creationDate">creationDate</option>
                            <option value="health">health</option>
                            <option value="heartCount">heartCount</option>
                            <option value="height">height</option>
                        </select>
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
                <div style={styles.response}>{listResponse}</div>
            </section>

            <hr style={styles.hr} />

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
                <div style={styles.response}>{byIdResponse}</div>
            </section>

            <hr style={styles.hr} />

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
                <div style={styles.response}>{postResponse}</div>


            </section>

            {/* 新增PUT更新接口 */}
            <hr style={styles.hr} />
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
                <div style={styles.response}>{putResponse}</div>
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
                    <div style={styles.response}>{deleteResponse}</div>
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
                    <div style={styles.response}>{deleteHeartCountResponse}</div>
                </div>
            </section>

            {/* 新增统计查询接口 */}
            <hr style={styles.hr} />
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
                    <div style={styles.response}>{countResponse}</div>
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
                    <div style={styles.response}>{namePrefixResponse}</div>
                </div>
            </section>
        </div>
    );
}
