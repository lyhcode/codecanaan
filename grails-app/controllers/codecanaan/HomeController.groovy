package codecanaan

import grails.plugin.springsecurity.annotation.Secured
import org.springframework.dao.DataIntegrityViolationException

class HomeController {

    def springSecurityService
    
    def grailsLinkGenerator

    /**
     * 首頁
     */
    def index() {
        // 如果已經登入
        // 先檢查 User.works（是否已經通過「開始使用」步驟）
        // 如果 User.works == FALSE 則先跳到步驟 II
       
        if (springSecurityService.isLoggedIn()) { 
            
            def user = springSecurityService.currentUser
            
            if (!user?.works) {
                redirect action: 'step1'
                return
            }
        }

        // 取得首頁公告資料    
        def posts = Post.findAll(max: 5, sort: 'dateCreated', order: 'desc') {
            type == PostType.ANNOUNCE
        }

        // 取得新書資料
        def books = Book.findAll(max: 5, sort: 'publishDate', order: 'desc') {
            title != null
        }

        [
            posts: posts,
            books: books,
            courses: Course.list()
        ]
    }
 
    /**
     * 「開始使用」步驟一、新會員註冊
     */
    def step1() {
        []
    }
   
    /**
     * 「開始使用」步驟二、安裝 Java 軟體
     */
    @Secured(['ROLE_USER'])
    def step2() {
        []
    }
    
    /**
     * 「開始使用」步驟三、啟動客戶端工具
     */
    @Secured(['ROLE_USER'])
    def step3() {
        def user = springSecurityService.currentUser
        
        [
            clientPort: user?.clientPort?:1337
        ]
    }
    
    /**
     * 「開始使用」步驟四、條款及獲取免費課程
     */
    @Secured(['ROLE_USER'])
    def step4() {
        def courses = OpenCourse.list()*.course
    
        [courses: courses]
    }
    
    /**
     * 已經完成啟用流程
     */
    @Secured(['ROLE_USER'])
    def works() {
    
        if (params.agree=='yes') {
        
            def user = springSecurityService.currentUser
            
            //設定是否接收訊息
            user.enableNews = (params.enableNews=='true')
            
            //設定使用者狀態為已啟用
            user.works = true
            
            //先儲存使用者資料
            user.save(flush: true)
            
            //註冊已選的開放式課程
            params.list('courses').each {
                cid->
                
                def c = Course.get(cid)
                def oc = OpenCourse.findByCourse(c)
                
                //先檢查課程是否真的是開放式課程
                if (oc) {
                    //只處理使用者尚未註冊的課程，避免重複註冊
                    def exists = UserCourse.findByUserAndCourse(user, c)
                    if (!exists) {
                        //設定為課程的註冊學生
                        UserCourse.create(user, c, RegType.STUDENT)
                    }
                }
            }
            
            //給予學生身份 ROLE_STUDENT
            def srole = Role.findByAuthority('ROLE_USER')
            UserRole.create(user, srole)
            
            //註冊到系統使用手冊課程
            def shc = Course.findByName('system-help')
            if (shc) {
                //只處理使用者尚未註冊的課程，避免重複註冊
                def exists = UserCourse.findByUserAndCourse(user, shc)
                if (!exists) {
                    UserCourse.create(user, shc, RegType.STUDENT)
                }
            }
            
            if (shc) {
                //直接進入系統使用手冊
                redirect controller: 'course', action: 'show', id: shc.id
            }
            else {
                //系統手冊不存在（錯誤？），回到我的課程
                redirect controller: 'course', action: 'list'
            }
            
            return
        }
        
        redirect url: '/'
    }

    /**
     * 客戶端工具
     */
    def client() {
        def user = springSecurityService.currentUser
       
        def section = 'client_'+(params.section?:'execute')

        [
            section: section,
            clientPort: user?.clientPort?:1337
        ]
    }

    /**
     * 測驗模式專頁
     */
    @Secured(['ROLE_USER'])
    def exam() {
        def user = springSecurityService.currentUser

        []
    }

    /**
     * A simple static page for template testing
     */
    def test() {
        []
    }
}
