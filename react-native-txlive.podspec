require "json"

package = JSON.parse(File.read(File.join(__dir__, "package.json")))

Pod::Spec.new do |s|
  s.name         = "react-native-txlive"
  s.version      = package["version"]
  s.summary      = package["description"]
  s.homepage     = package["homepage"]
  s.license      = package["license"]
  s.authors      = package["author"]

  s.platforms    = { :ios => "9.0" }
  s.source       = { :git => "https://github.com/kafudev/react-native-txlive.git", :tag => "#{s.version}" }


  s.source_files = "ios/**/*.{h,m,mm}"


  s.dependency "React"
  # s.dependency 'TXIMSDK_iOS'

  # s.dependency 'RxSwift'
  # s.dependency 'RxCocoa'
  # s.dependency 'SnapKit'
  # s.dependency 'Alamofire'
  # s.dependency 'Toast-Swift'
  # s.dependency 'Material'
  # s.dependency 'NVActivityIndicatorView'

  # s.dependency "SDWebImage"
  # s.dependency "Masonry"
  # s.dependency "MBProgressHUD", '~> 1.2.0'
  # s.dependency "MJExtension"
  # s.dependency "MJRefresh"
  # s.dependency "AFNetworking"
  # s.dependency 'BlocksKit', '~> 2.2.5'
  # s.dependency 'CWStatusBarNotification', '~> 2.3.5'
  s.dependency 'TXLiteAVSDK_Professional', '~> 8.5' #, :podspec => 'http://pod-1252463788.cosgz.myqcloud.com/liteavsdkspec/TXLiteAVSDK_Professional.podspec'
end
