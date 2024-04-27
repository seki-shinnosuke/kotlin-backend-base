# DB
## Directory
```
db
├── ddl           //DDLおよびDMLを配置
│   └── migration
├── flyway        //Flywayの設定ファイルを配置
└── mybatis       //MybatisGeneratorの設定ファイルを配置
```

## Flyway
ローカル環境ではDBマイグレーションツールとしてFlywayを利用しています  
DDL変更時は以下のコマンドを実行することで反映されます  
※単体テストのDBには対応していないためDDL変更時はdocker-composeをdownさせてください  
```
% ./gradlew flywayInit
```

マイグレーションツールは本来、変更差分のみを格納していき反映を行うものですがテーブル構造がDDL文から読み取れなくなるので
クエリー毎の一元管理とし、開発・本番環境では変更反映用のDDL文を個別で作成してください

## MybatisGenerator
本プロジェクトではROマッパーとしてMybatisを利用しています  
対象テーブルに対する単一操作を簡略化させるためにMybatisGeneratorを利用してテーブル毎のモデルとマッパーを自動生成してください  
ローカル環境のDBにDDLを反映後、生成対象のテーブルを設定ファイルに追記し以下のコマンドを実行することで生成されます  
```
% ./gradlew mybatisGenerator
```

モデルとマッパーはcommon.db.modelとcommon.db.mapper配下に出力されます  
上書き出力となるためテーブル名変更や削除時は個別に削除してください  
