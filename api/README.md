# API
## Directory
```
api
├── config         //各フレームワークに依存したAPI用設定クラスを配置
├── controller     //コントローラークラスを配置
├── filter         //AOPクラスを配置
├── handler        //各フレームワークに依存したハンドラークラスを配置
├── model          //APIのリクエスト/レスポンスモデルを配置
│   ├── mapper
│   └── model
├── service        //サービスクラスを配置
└── ApiApplication //APIアプリケーション起動用クラス
```

## Process
```
ApiApplication
└── Controller
    └── Service
        └── Common:db
```
1. ApiApplicationが起動
2. Controllerクラスに定義されたAPI PathをCall
3. Serviceクラスを呼び出し対象データの処理を行いレスポンスを返却

## OpenAPI
API仕様はOpenAPIのYAML形式で記載しています  
仕様は機能単位(Path)で作成してください
```
doc
├── components    //共通で利用するモデルを配置
│   ├── responses
│   └── schemas
├── paths         //APIのパス毎に配置
└── openapi.yaml
```
