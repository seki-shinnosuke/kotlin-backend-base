# Batch
## Directory
```
batch
├── core             //バッチフレームワークを配置
├── logic            //各バッチロジックを配置
│   ├── daily        //日次バッチを配置
│   │   ├── service  //ビジネスロジックを配置
│   │   └── runner   //ビジネスロジック起動用クラス
│   ├── minutely     //分次バッチを配置
│   └── monthly      //月次バッチを配置
└── BatchApplication //バッチアプリケーション起動用クラス
```

## Process
```
BatchApplication
└── Runner
    └── Service
        └── ServiceRecord
```
1. バッチサーバからBATCH_IDを付与した形でJarファイルをキック
2. BatchApplicationが起動
3. BATCH_IDから同じidを設定したRunnerが起動
4. Serviceクラスを呼び出し対象データをDBから抽出
5. ServiceRecordクラスを呼び出し対象データを1件ずつ処理  
