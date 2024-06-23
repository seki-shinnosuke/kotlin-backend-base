# Welcome to `kotlin-backend-base`

## What is repository
本リポジトリはKotlin x SpringBootで商用環境でも利用できるレベルのガチバックエンドをナレッジ共有するために用意したものです

## 主要技術
| 名称         | バージョン               | 用途                       |
|------------|---------------------|--------------------------|
| OpenJDK    | 21(Amazon Corretto) | サーバ開発キット                 |
| Kotlin     | 1.9.22              | サーバ言語                    |
| SpringBoot | 3.2.3               | サーバFW                    |
| MySQL      | 8.0.33              | RDB                      |
| Redis      | 7.2.3               | セッションストア                 |

## Project architecture
本プロジェクトはマルチプロジェクト構成を採用しています  
各サブプロジェクトのREADMEを参照してください

## Advance preparation
以下をPCにインストール&準備をする必要があります(macOS/zshでの開発が前提となります)
1. OrbStack ※ライセンスがある場合はDockerDesktop
2. IntelliJ (サーバサイド)
3. Homebrew

#### 1.OrbStack install
https://orbstack.dev/download

#### 2.IntelliJ install
https://www.jetbrains.com/ja-jp/idea/download/#section=mac

#### 3.Homebrew install
https://brew.sh/index_ja

#### 4.JDK setup
主要技術に記載されているJDKを設定します  
以下の記事を参考にしてください  
https://pleiades.io/help/idea/sdk.html#set-up-jdk

#### 5.Docker image build
```
% make install
% make run
```

#### 6.RDB setup
```
% ./gradlew flywayInit
```

### Tips
ローカル環境でapiおよびbatchの実行はIntelliJからのでバック実行を想定していますが  
コンテナ化と起動を試したい場合はmakeコマンドを利用してください  
```
# API実行
% make api-build
% make api-run-local

# Batch実行
% make batch-build-local
% make batch-run-local BATCH_ID={バッチID}
```

## Access point
ローカル開発での生産性向上のため、必要なミドルウェアはコンテナ化しています  
各種ポート設定は以下となっています    
| アプリ | BackEnd/Docker | URL |
| ---- | ---- | ---- |
| API | BackEnd | http://localhost:18080 |
| DB | Docker | http://localhost:23306 |
| Redis | Docker | http://localhost:26379 |
| Minio(S3代替) | Docker | http://localhost:29092 (API は 29090) |
| Node-Red(スタブ) | Docker | http://localhost:21880 |
| ReDoc(OpenAPIドキュメント) | Docker | http://localhost:3000 |
