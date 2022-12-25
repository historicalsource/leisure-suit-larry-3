;**
;**	Logics for room 585 -- Adventureland village
;**
;**	LSL3:  Passionate Patti in Pursuit of the Pulsating Pectorals
;**	       Copyright 1989
;**		by Al Lowe, Bill Skirvin, C. Escobar, and Roger Hardy
;**
;**	Last Update:	October 19, 1989
;**


(script#				585)

(public
	rm585				0
)


(define	vAmazon1						582)
(define	vAmazon2						583)
	(define	lWalkingWest	 	0)
	(define	lJumping				1)
	(define	lIntoWater			2)
	(define	lLeavingWater		3)
	(define	lWading				4)
	(define	lWalkingNW			5)
	(define	lWalkingNWsmall	6)

(define	vAmazonExtras1				584)
	(define	lWhipMeBaby			1)
	(define	lCMRight				2)
	(define	lPutDown				3)
	(define	lPutDown1			4)
	(define	lCMLeft				5)
	(define	lOldMan				6)
	(define	lBowl					7)

(define	vAmazonExtras2				585)
	(define	lDrummer1   		0)
	(define	lDrummer2  			1)
	(define	lSlave				2)


;(define	sFadeToBlack2				485)


(enum												;**	RoomScript state values
	intro
	bringUmIn
	gotoBlack
	ohPatti
	hairdresser
	timePasses
	blindDate
	youRegain
	changeRooms
)


(enum												;**	ManScript state values
	ManIntro
	startWomenIn
	putCornDown
	bentOver
	walkingBack
	setHisPri
	eraseMan
)


(instance rm585 of Room
	(properties
		picture		585
	)


	(method (init)
		(HandsOff)
		(Load SOUND sFadeToBlack2)
		(Load SCRIPT DPATH)
		(Load PICTURE blackPicture)

		(super init)

		(aOldMan					init)
		(aWhipper				init)
		(aDrummer1				init)
		(aDrummer2				init)
		(aSlave					init)
		(aCornMan 				init)

		(aWoman					init)
		(ego				 
			illegalBits		0,
			ignoreActors,
			view				vAmazon1,
			setLoop			lWalkingNW,
			setCycle			Walk,
			setStep			2 2,
			setPri			RELEASE,
			posn				332 161,
			init,
		)

		(self
			setScript 		RoomScript
		)
	)
)


(instance RoomScript of Script
	(method (doit)
		(super doit)

		(if (> machineSpeed pc8088) 		
			(switch (Random 1 22)
				(1 	(aOldMan		setCycle Forward))
				(2 	(aOldMan		stopUpd))
				(3 	(aWhipper	setCycle EndLoop))
				(4 	(aWhipper	stopUpd))
				(5 	(aDrummer1	setCycle Forward))
				(6 	(aDrummer1	stopUpd))
				(7 	(aDrummer2	setCycle Forward))
				(8 	(aDrummer2	stopUpd))
			)
		)
	)


	(method (changeState newState)
		(ShowState self newState 1 green)

		(switch (= state newState)

			(intro
			)	

			(bringUmIn
				(Print "Knocked unconscious, 
					securely wrapped in the Amazonian woman cannibal's net,
					you hang over her shoulder 
					like a sack of potatoes in an Army mess.")
				(ego
					setMotion		DPath	343 162 
												343 162 
												294 149 
												222 112 
												206 80  
												201 72  
												201 72  
												197 58
												190 48,
				)
				(aWoman
					setMotion 		DPath	329 166
												306 154 
												265 139 
												207 107 
												195 78  
												192 63
												187 54
												self,
				)
			)

			(gotoBlack
				(curRoom drawPic blackPicture IRISIN)
				(cast eachElementDo #hide)
				(delay 3)
			)

			(ohPatti
				(Print "Oh, Patti! 
					Where will they take you?
					What will they do to you?
					What will happen to you?")
				(delay 3)
			)

			(hairdresser
				(Print "(And will there be a hairdresser available 
					to you afterwards?)" LOW)
				(delay 3)
			)

			(timePasses
				(Print "You are jostled through the village,
					paraded about the square,
					handled roughly, battered and bruised,
					then placed inside a cage made of bamboo and leather.")
				(Print "...an experience not wholly unfamiliar to you!")
				(delay 3)
			)

			(blindDate
				(Print "(You fondly recall a blind date when you were
					at Juilliard...)" LOW)
				(delay 3)
			)

			(youRegain
				(Print "As you slowly regain consciousness,
					the blackness leaves your brain and
					you realize you are no longer tied up in that net.
					But something tells you you are no longer alone.")
				(delay 3)
			)

			(changeRooms
				(music
					number			sFadeToBlack2,
					loop				FOREVER,
					play ,
				)
				(DisposeScript DPATH)
				(curRoom newRoom 590)
			)
		)
	)
)


(instance aBowl of View
	(properties
		view 				vAmazonExtras1
		loop				lBowl
		x					284
		y					114
	)
	(method (init)
		(super init)
		(self
			ignoreActors,
			stopUpd,
		)
	)
)

(instance aDrummer1 of Prop
	(properties
		view 				vAmazonExtras2				
      loop				lDrummer1
		x					148
		y					055
	)
	(method (init)
		(super init)
		(self
			setCycle		Forward,
			stopUpd,
		)
	)
)
(instance aDrummer2 of Prop
	(properties
		view 				vAmazonExtras2				
      loop				lDrummer2
		x					255
		y					057
	)
	(method (init)
		(super init)
		(self
			setCycle		Forward,
			stopUpd,
		)
	)
)
(instance aOldMan of Prop
	(properties
		view 				vAmazonExtras1
      loop				lOldMan
		x					287
		y					107
	)
	(method (init)
		(super init)
		(self
			stopUpd,
		)
	)
)
(instance aWhipper of Prop
	(properties
		view 				vAmazonExtras1
      loop				lWhipMeBaby
		x					086
		y					138
	)
	(method (init)
		(super init)
		(self
			stopUpd,
		)
	)
)

(instance aSlave of Prop
	(properties
		view 				vAmazonExtras2				
		loop				lSlave
		x					134
		y					135
	)
	(method (init)
		(super init)
		(self
			setCycle		Forward,
			stopUpd,
		)
	)
)


(instance aCornMan of Actor
	(properties
		view 				vAmazonExtras1
		x					89
		y					89
		illegalBits		0
	)

	(method (init)
		(super init)
		(self
			setPri			3,
			setLoop			lCMRight,
			setCycle			Walk,
			ignoreActors,
			setScript		ManScript,
		)
	)
)
(instance ManScript of Script
	(method (changeState newState)	  
		(ShowState self newState 1 green)

		(switch (= state newState)
			

			(ManIntro
				(aCornMan
					setMotion		MoveTo 159 87 self,
				)
			)	

			(startWomenIn
				(aCornMan
					setMotion		DPath 170 90
												233 100
												261 113
												self,
				)
			)

			(putCornDown
				(aCornMan
					setPri			RELEASE,
					setLoop			lPutDown,
					cel				0,
					setCycle			EndLoop self,
				)
			)

			(bentOver
				(aCornMan
					setLoop			lPutDown1,
					setCycle			EndLoop self,
				)
				(aBowl				init)
			)

			(walkingBack
				(aCornMan
					setCycle			Forward,
					setLoop			lCMLeft,
					setMotion 		DPath	233 100
												170 90
												122 84
												self,
				)
			)

			(setHisPri
				(RoomScript changeState bringUmIn)
				(aCornMan
					setPri			3,
					setMotion		MoveTo 84 91 self,
				)
			)

			(eraseMan
				(aCornMan			hide)
			)
		)
	)
)

(instance aWoman of Actor
	(properties
		view 				vAmazon2
		x					330
		y					163
	)

	(method (init)
		(super init)
		(self
			setStep			2 2,
			setLoop			lWalkingNW,
			setCycle			Walk,
			illegalBits		0,
		)
	)
)

