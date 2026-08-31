[![English](https://img.shields.io/badge/lang-English-blue.svg)](https://github.com/Ekristoffe/WebVisu-Starter) / [![日本語](https://img.shields.io/badge/lang-日本語-red.svg)](README.ja.md)

# WebVisu Starter
 CoDeSys 2.3 または WAGO-I/O-PRO CAA IDE を使用する Ethernet コントローラ向けの WebVisu 機能は、Web ブラウザを通じてリアルタイムモニタリングを可能にします。従来、この機能は Oracle Java™ Applet テクノロジーに依存していました。  

 2017 年に Java™ はバージョン 9 へアップグレードされ、大幅なセキュリティ強化が実施されました。このアップデートの一環として、Java™ Applet のサポートは廃止されました。  
 さらに、最新の Web ブラウザにおいても Java™ Applet のサポートは終了しています。その結果、Java™ バージョン 9 未満を使用している場合でも、Java™ Applet を起動することはできなくなっています。  

 そのため、更新された Web ブラウザや PC から Ethernet コントローラに保存されている WebVisu 機能へアクセスした際、画面が表示されなくなる場合があります。  

 この問題に対応するため、本シンプルな Java™ アプリケーションを作成しました。本アプリケーションを使用することで、最新の Web ブラウザが Java™ Applet をサポートしていない環境でも、既存の WebVisu 画面を引き続き利用することができます。  

 また、Oracle は 2019 年 1 月（バージョン 8u202 以降）に Java™ のライセンス体系を変更しました。現在では、Java™ を開発、テスト、試作、またはデモ目的で利用する場合を除き、商用利用には有償ライセンスが必要となっています。  

 このため、JRE（Java Runtime Environment）として Eclipse Temurin の利用を推奨します。  

 Eclipse Temurin は Eclipse Foundation の商標です。これは Java™ Platform, Standard Edition（Java™ SE）のオープンソース実装である OpenJDK のディストリビューションです。Java™ 自体は Oracle の商標ですが、Eclipse Temurin は Java™ アプリケーションを実行するための無償かつオープンソースの代替環境を提供します。  


ドキュメント
-------------
 <details>

 <summary>Java™ をインストール</summary>

 ### OpenJDK (JRE)をダウンロード

 Java™ が PC にインストールされていない場合は、Eclipse Adoptium の Web サイトからダウンロードできます。

 <https://adoptium.net/temurin/releases?mode=filter>

 リリースフィルターで Version **8 - LTS** を選択します。  
 オペレーティングシステムで **Windows** を選択します。  
 一覧から **JRE** と、お使いのアーキテクチャ（x64 または x86（32bit））を選択します。  
 その後、JRE インストーラをダウンロードするため、**.MSI** の左側にあるダウンロードアイコンをクリックしてください。  

 ### Java™ JRE をインストール
 インストーラを起動します。  
 ![Temurin 1](https://github.com/Ekristoffe/WebVisu-Starter/blob/main/images/Temurin_JP_1.png?raw=true)  

 ライセンス契約の内容を確認し、[次へ] をクリックします。  
 ![Temurin 2](https://github.com/Ekristoffe/WebVisu-Starter/blob/main/images/Temurin_JP_2.png?raw=true)  

[Install for all users of this machine] を選択し、[次へ] をクリックします。  
 ![Temurin 3](https://github.com/Ekristoffe/WebVisu-Starter/blob/main/images/Temurin_JP_3.png?raw=true)  

[JRE with Hotspot] を展開し、[ローカル ハード ドライブにすべてインストール] を選択して、[次へ] をクリックします。  
 ![Temurin 4](https://github.com/Ekristoffe/WebVisu-Starter/blob/main/images/Temurin_JP_4.png?raw=true)  

[インストール] をクリックしてインストールを開始します。管理者権限が必要な場合があります。  
 ![Temurin 5](https://github.com/Ekristoffe/WebVisu-Starter/blob/main/images/Temurin_JP_5.png?raw=true)  

インストールが完了したら、[完了] をクリックします。  
 ![Temurin 6](https://github.com/Ekristoffe/WebVisu-Starter/blob/main/images/Temurin_JP_6.png?raw=true)  

 </details>  

 ### Webvisu Starter

 右側のリンクから Releases ページ を開きます。  
 <https://github.com/Ekristoffe/WebVisu-Starter/releases>

 最新版の Webvisu_Starter.jar をクリックしてダウンロードを開始します。  

　ダウンロード完了後、Webvisu_Starter.jar をダブルクリックして起動します。  

　以下の画面が表示されます。  

 ![Webvisu_Starter](https://github.com/Ekristoffe/WebVisu-Starter/blob/main/images/Webvisu_Starter.png?raw=true)  

 「IP Address」 欄にコントローラのIPアドレスを入力します。  
　「Ping Req」 にチェックを入れると、WebVisu起動前にPINGコマンドによる通信確認が実行されます。  
　「Start」 ボタンをクリックすると、WebVisu機能が起動します。  

<br>

免責事項
-------------

<div style="background-color: lightgray;">
<span style="color: red">
 本ソフトウェアは「現状のまま」で提供されるものであり、明示的または黙示的を問わず、商品性、特定目的への適合性、および第三者の権利を侵害しないことを含む、いかなる保証も行いません。 著作者または著作権者は、本ソフトウェアまたはその使用、もしくはその他の取り扱いに起因または関連して発生したいかなる請求、損害、その他の責任についても、契約行為、不法行為、またはその他の法的根拠のいずれによる場合であっても、一切の責任を負わないものとします。
</span>
</div>
