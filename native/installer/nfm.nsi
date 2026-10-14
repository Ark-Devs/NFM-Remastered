; Need for Madness Remastered - Windows installer (build with: sh build_installer.sh)
Unicode true
SetCompressor /SOLID lzma
!include "MUI2.nsh"

!define APP "Need for Madness Remastered"
!define EXE "NeedForMadness.exe"
!define KEY "Software\Microsoft\Windows\CurrentVersion\Uninstall\NeedForMadnessRemastered"
!define VERSION "1.0.0"

Name "${APP}"
OutFile "..\build\NeedForMadness-Setup.exe"
; per-user install: no administrator prompt needed
RequestExecutionLevel user
InstallDir "$LOCALAPPDATA\Programs\Need for Madness Remastered"
InstallDirRegKey HKCU "${KEY}" "InstallLocation"
BrandingText "${APP} ${VERSION}"

!define MUI_ICON "nfm.ico"
!define MUI_UNICON "nfm.ico"
!define MUI_WELCOMEFINISHPAGE_BITMAP "wizard.bmp"
!define MUI_UNWELCOMEFINISHPAGE_BITMAP "wizard.bmp"
!define MUI_ABORTWARNING
!define MUI_FINISHPAGE_RUN "$INSTDIR\${EXE}"
!define MUI_FINISHPAGE_RUN_TEXT "Play ${APP} now"
!define MUI_WELCOMEPAGE_TEXT "This will install ${APP} on your computer."

!insertmacro MUI_PAGE_WELCOME
!insertmacro MUI_PAGE_DIRECTORY
!insertmacro MUI_PAGE_INSTFILES
!insertmacro MUI_PAGE_FINISH
!insertmacro MUI_UNPAGE_CONFIRM
!insertmacro MUI_UNPAGE_INSTFILES
!insertmacro MUI_LANGUAGE "English"

VIProductVersion "${VERSION}.0"
VIAddVersionKey "ProductName" "${APP}"
VIAddVersionKey "FileDescription" "${APP} Setup"
VIAddVersionKey "FileVersion" "${VERSION}"
VIAddVersionKey "ProductVersion" "${VERSION}"
VIAddVersionKey "LegalCopyright" "Need For Madness Remastered"

; The game can't be replaced while it's open: say so plainly and offer to
; close it, instead of Windows' "error opening file for writing".
Function CloseRunningGame
  retry:
  IfFileExists "$INSTDIR\${EXE}" 0 done
  ClearErrors
  FileOpen $0 "$INSTDIR\${EXE}" a
  IfErrors busy
  FileClose $0
  Goto done
  busy:
  MessageBox MB_YESNOCANCEL|MB_ICONEXCLAMATION "${APP} is still open, so it can't be updated.$\r$\n$\r$\nClick Yes to close the game and continue, or close it yourself and click No to try again." IDYES kill IDNO retry
  Abort
  kill:
  nsExec::Exec 'taskkill /F /IM "${EXE}"'
  Sleep 1500
  Goto retry
  done:
FunctionEnd

Section "Game" SecGame
  SectionIn RO
  SetOutPath "$INSTDIR"
  Call CloseRunningGame
  File "..\build\${EXE}"
  File "..\build\nfm.dll"
  ; Epic Online Services: online room list and relayed connections
  File "..\build\libeosg.windows.template_release.x86_64.dll"
  File "..\build\EOSSDK-Win64-Shipping.dll"
  File "..\build\xaudio2_9redist.dll"
  File "nfm.ico"
  WriteUninstaller "$INSTDIR\Uninstall.exe"

  CreateDirectory "$SMPROGRAMS\${APP}"
  CreateShortcut "$SMPROGRAMS\${APP}\${APP}.lnk" "$INSTDIR\${EXE}" "" "$INSTDIR\nfm.ico"
  CreateShortcut "$SMPROGRAMS\${APP}\Uninstall.lnk" "$INSTDIR\Uninstall.exe"
  CreateShortcut "$DESKTOP\${APP}.lnk" "$INSTDIR\${EXE}" "" "$INSTDIR\nfm.ico"

  ; shows up in Settings > Apps for removal
  WriteRegStr HKCU "${KEY}" "DisplayName" "${APP}"
  WriteRegStr HKCU "${KEY}" "DisplayVersion" "${VERSION}"
  WriteRegStr HKCU "${KEY}" "DisplayIcon" "$INSTDIR\nfm.ico"
  WriteRegStr HKCU "${KEY}" "InstallLocation" "$INSTDIR"
  WriteRegStr HKCU "${KEY}" "UninstallString" '"$INSTDIR\Uninstall.exe"'
  WriteRegDWORD HKCU "${KEY}" "NoModify" 1
  WriteRegDWORD HKCU "${KEY}" "NoRepair" 1

  ; an update keeps the same file names, so make Windows redraw the icons
  ; instead of showing cached old ones
  System::Call 'shell32::SHChangeNotify(i 0x08000000, i 0, p 0, p 0)'
  nsExec::Exec '"$SYSDIR\ie4uinit.exe" -show'
SectionEnd

Section "Uninstall"
  Delete "$INSTDIR\${EXE}"
  Delete "$INSTDIR\nfm.dll"
  Delete "$INSTDIR\libeosg.windows.template_release.x86_64.dll"
  Delete "$INSTDIR\EOSSDK-Win64-Shipping.dll"
  Delete "$INSTDIR\xaudio2_9redist.dll"
  Delete "$INSTDIR\nfm.ico"
  Delete "$INSTDIR\OFL.txt"
  Delete "$INSTDIR\CREDITS.txt"
  Delete "$INSTDIR\Uninstall.exe"
  RMDir "$INSTDIR"
  Delete "$SMPROGRAMS\${APP}\${APP}.lnk"
  Delete "$SMPROGRAMS\${APP}\Uninstall.lnk"
  RMDir "$SMPROGRAMS\${APP}"
  Delete "$DESKTOP\${APP}.lnk"
  DeleteRegKey HKCU "${KEY}"
SectionEnd
