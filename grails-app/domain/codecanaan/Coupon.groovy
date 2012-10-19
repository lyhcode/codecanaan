package codecanaan

class Coupon {
	String serialCode			//序號
	boolean registered = false	//已被註冊
	boolean valid = true		//是否有效
	String organization			//發券單位

	User user 			//序號使用者
	Course course 		//授權課程

	Date dateCreated    //建立日期
    Date lastUpdated    //修改日期

    //static belongsTo = [course: Course]

    static constraints = {
    	serialCode nullable: false, empty: false
    	user nullable: true
    	course nullable: false
    	organization nullable: true, empty: true
    }
}
