;**
;**	Logics for room 320 -- Dewey, Cheatem & Howe
;**
;**	LSL3:  Passionate Patti in Pursuit of the Pulsating Pectorals
;**	       Copyright 1989
;**		by Al Lowe, Bill Skirvin, Carlos Escobar, and Roger Hardy
;**
;**	Last Update:	October 25, 1989
;**


(script#				320)

(public
	rm320				0
)


;**		Suzi's secretary has had Patti.  Suzi is occupied until Cherri has
;**	hinted she wants land.  L can enter her Suzi's office once for free,
;**	to gain admission a second time, must give $500 to the secetary.
;**		If he doesn't ask Suzi for the land deed, she tells him as he leaves
;**	and he gets no points.
;**	Suzi often faxes stuff to secretary.
;**	The deed and divorce may be picked up here when they are read.
;**		If L doesn't ask Suzi for his divorce during the $500 visit, she
;**	tells him as he leaves and no points.
;**		With the divorce decree, you also get the spa card.

;**		lawyerState gets changed in these rooms at these times:
;**	LSbusy								;**	000 at start of game
;**	LSfree								;**	435 when L learns C wants a farm
;**	LSwaiting4deed						;**	323 at end of Suzi's free chat
;**	LSdeedReady							;**	320 as L leaves 320 for 220
;**	LSneeds500							;**	320 as L gets the deed from Roger
;**	LSdivorce							;**	320 when L gives $500 to Roger
;**	LSwaiting4divorce					;**	323 during $500 chat (or 324)
;**	LSdivorceReady						;**	320 as L leaves 320 for 220
;**	LSdone								;**	320 when L picks up divorce 


(define	vRoom							320)
	(define	lDCHSign				0)
	(define	lSign					1)
	(define	lDeskStuff			2)
		(define cComputer		0)
		(define cPencils  	1)
	(define	lDoor					3)
	(define	lFaxMachine			4)
	(define	lFax					5)

(define vSecretary					321)
	(define	lPhoneToEar			0)
	(define	lTalkOnPhone		1)
	(define	lWriting				2)
	(define	lTalking				3)
	(define	lYawning				4)
	(define	lFaceIcon			5)


;(define	sSuzi							323)	


(define nearSecretary		 	(& (ego onControl) cMagenta))
(define nearDeweyDoor 			(& (ego onControl) cCyan))
(define nearCheatemDoor 		(& (ego onControl) cBlue))
(define nearHoweDoor 			(& (ego onControl) cRed))


(enum												;**	RoomScript state values
	intro
	changeSongs
	goSeeSuzi
	clerkDoneTalking
	clearedDesk
	clearedScreen
	atDoor
	doorHasOpened
)

(enum												;**	SecretaryScript state values
	SecretaryInit
	whatWillHeDoNext
	startTalking
	sayWhatYouMean
	startWriting
	stopIt
	startYawning
	yawnAlittle
	stopYawning
	startPhone
	chatAlittle
	chatAlittleMore
	putPhoneDown
)


(local
	[string 300]
	LarrySpoke
	SecretaryState
		(enum 1									;**	SecretaryState values
			GETS_FREEBIE
			PAID_500
			320
			WRITING
			PHONING
			TALKING
			YAWNING
		)
)


(synonyms
	(man secretary clerk roger )
)


(instance rm320 of Room
	(properties
		picture		320
		south			310
	)

	(method (init)
		(if (= dollars 500)
			(Load VIEW ivOnes)
		)

		(Load SOUND sSuzi)

		(super init)

		(addToPics
			add 				atpDeskStuff,
			add 				atpPencils,
			add 				atpDCHSign,
			add 				atpSign,
			add 				atpFax,
			doit
		)

		(aDoor				init,
			locked 			TRUE,
		)

		(if egoIsPatti
			(= lawyerState LSdone)
		)

		(if ( < lawyerState LSdone)
			(aRoger			init)

			(if ( > machineSpeed pc8088)
				(aFax			init)
			)
		)

		(self
			setScript 		RoomScript
		)

		(if (or	(== prevRoomNum 323)
					(== prevRoomNum 324))
			(ego
				loop			facingSouth,
				posn			153 96,
			)
		else
			(ego
				loop			facingNorth,
				posn			156 186,
			)
		)

		;**	just finished screwing, or if from outside or boot up
		(if (!= prevRoomNum 323)		
			(music
				number		sSuzi,
				loop			bgMusicLoops,
				play,
			)
		)
		(NormalEgo)
		(ego					init)
	)


	(method (newRoom n)
		(cond
			((and	(== lawyerState LSwaiting4deed)
					(== (ego edgeHit) SOUTH))
				(= lawyerState LSdeedReady)	;**	It'll be here when you return.
			)
			((and	(== lawyerState LSwaiting4divorce)
					(== (ego edgeHit) SOUTH))
				(= lawyerState LSdivorceReady);**	It'll be here when you return.
			)
		)
		(super newRoom n)
	)
)


(instance RoomScript of Script
	(method (handleEvent event)
		(if (or	(!= (event type) saidEvent) (event claimed)) (return))

		(cond
			((Said 'get/tree')
				(Print "You may need some wood,
					but this is not the right place to get it.")
			)

			((Said '/fax')
				(Print "It's for internal use only.")
				(Print "(Corporate internals, that is!)" LOW)
			)

			((Said '/computer')
				(Print "You've been looking at a computer ever since you
					started playing this game!")
			)

			((Said '/telephone')
				(Print "There is a telephone on the desk,
					but don't get any cute ideas about using it!")
			)

			((Said 'look>')
				(cond
					((Said '/tree')
						(Print "With all the trees on this island,
							wouldn't you think they'd have enough outside!?")
					)

					((Said '/sign, wall')
						(Print "You surmise those large initials represent the
							first letter of the last names of the three partners
							in this law firm.")
					)

					((Said '/light, ceiling')
						(Print "A suspended florescent fixture hangs above the
							receptionist's desk.")
					)

					((Said '/desk, counter, table')
						(Print "Roger's desk contains a computer terminal,
							a fax machine and a telephone.")
					)

					((Said '/door')
						(cond
							(nearDeweyDoor
								(Print "You are standing outside the door to
									Mr. Dewey's private office.")
							)
							(nearCheatemDoor
								(Print "This door leads to Ms. Suzi Cheatem's
									private office.")
							)
							(nearHoweDoor
								(Print "This is the door to Mr. Howe's private
									office.")
							)
							(else
								(Print "There are three doors leading to the private
									offices of the firm's attorneys,
									as well as a door to the south leading outside.")
							)
						)
					)

					((Said '[/room]')
						(Print "You are in the offices of the distinguished island
							legal firm of Dewey, Cheatem, and Howe.")
						(if (cast contains aRoger)
							(Print "There is a male receptionist seated at the table.")
						)
					)
				)
			)
		)
	)


	(method (changeState newState)
		(ShowState self newState 1 red)

		(switch (= state newState)

			(intro
			)

			(changeSongs
			)

			(goSeeSuzi
				(HandsOff)						;**	and wait here for cue
				(theGame changeScore 10)
			)

			(clerkDoneTalking
				(HandsOff)						
				(aDoor locked FALSE)
				(ego
					illegalBits		0,
					setMotion		MoveTo 233 (ego y) self,
				)
			)

			(clearedDesk
				(ego
					setMotion		MoveTo 233 130 self,
				)
				(if (== egoState WEARING_ZIEGFIELD)
					(Format @string
						"\"Oh, by the way:
						Nice outfit, Mr. Laffer! (Hee, hee.)\"")
					(SecretaryScript changeState startTalking)
				)
			)

			(clearedScreen
				(ego
					setMotion		MoveTo 153 93 self,
				)
			)

			(atDoor
				(ego
					setMotion		MoveTo 153 87 self,
				)
			)

			(doorHasOpened
				(if (== egoState WEARING_ZIEGFIELD)
					(curRoom newRoom 324)
				else
					(curRoom newRoom 323)
				)
			)
		)
	)
)


(instance SecretaryScript of Script
	(method	(handleEvent event)
		(if (or (!= (event type) saidEvent) (event claimed)) (return))

		(cond
			((Said 'look/man')
				(Print "Roger is the executive secretary in charge of faxing
					and reception here at the legal firm of Dewey, Cheatem & Howe.")
			)

			((or	(Said '/appointment')
					(Said '//appointment')
					(Said 'talk'))
				(cond
					((not nearSecretary)
						(NotClose)
					)
					((and	(!= egoState NORMAL)
							(!= egoState WEARING_ZIEGFIELD))
						(NotNow)
					)
					((!= SecretaryState NORMAL)
						(Print "The receptionist seems to be busy right now.")
					)
					(else
						(User canInput FALSE)
						(switch lawyerState 
							(LSbusy
								(Print "\"Is there a lawyer available that could help me?\"
									you ask the clerk.")
								(Format @string "\"I'm sorry, sir,
									but Mr. Dewey is in conference,
									Ms. Cheatem is taking depositions,
									and Mr. Howe is in court.
									Could you please drop by again later?\"")
								(self changeState startTalking)
							)
							(LSfree
								(Print "\"Is there a lawyer available that could help me?\"
									you ask the clerk.")
								(Format @string "\"Yes, we have someone available.
									What specifically do you want, sir?
									We have all sorts of attorneys here.\"")
								(self changeState startTalking)
							)
							(LSwaiting4deed
								(Print "\"Is that deed ready?\" you ask the clerk.")
								(Format @string "\"Ms. Cheatem has instructed me to
									prepare your land deed.
									I'll have it ready soon.
									Why don't you come back in a few minutes, Mr. Laffer.\"")
								(self changeState startTalking)
							)
							(LSdeedReady
								(Print "\"Hi, Roger,\" you greet the clerk.")
								(Format @string "\"Hello again, Mr. Laffer.
									I bet I know why you're here!\"")
								(self changeState startTalking)
							)
							(LSneeds500
								(Print "\"I'm back, Roger,\" you greet the clerk.")
								(Format @string "\"Nice to see you, Mr. Laffer.
									Did you bring $500.00 with you?\"")
								(self changeState startTalking)
							)
							(LSwaiting4divorce
								(Print "\"Is Ms. Cheatem always like that?\" you ask Roger.")
								(Format @string "\"Yep! Isn't it nice to meet a woman
									that's really `into' her work!\"")
								(self changeState startTalking)
							)
							(LSdivorceReady
								(Print "\"Hi, Roger,\" you greet the clerk.")
								(Format @string "\"Hello again, Mr. Laffer.
									I bet I know why you're here!\"")
								(self changeState startTalking)
							)
							(LSdone
								(Print "\"Thanks for all your help, Roger,\" you tell him.")
								(Format @string "\"You're welcome, Mr. Swinging Single!\"")
								(self changeState startTalking)
							)
						)
					)
				)
			)

			((and (ego has iKeycard)		
					(or	(Said '/keycard, (card<key, spa, membership)')
							(Said '//keycard, (card<key, spa, membership)')))
				(Print "\"Roger, look here. 
					I found a membership card to Fat City in my deed.\"")
				(Format @string "\"Just keep it, Mr. Laffer.
					It's the least Ms. Cheatem can do for you!\"")
			)

			((or	(Said '/patti')
					(Said '//patti'))
				(User canInput FALSE)
				(Print "\"Say, Roger.
					What do you know about that entertainer over at the
					casino lounge?\" you ask him.")
				(Format @string "\"From personal experience,
					I can verify she's pretty fast_--
					and I'm not talking about fingering her keyboard!\"")
				(self changeState startTalking)
			)

			((or	(Said '/lawyer, suzi, cheatem')
					(Said '//lawyer, suzi, cheatem'))
				(User canInput FALSE)
				(Format @string "\"Yes, we have lawyers galore around here.
					Why do you ask?\"")
				(self changeState startTalking)
			)

			((or	(Said 'yes')
					(Said 'give, pay, use/money, man, fee, ones, bill, 500')
					(Said 'give, pay, use//money, man, fee, ones, bill, 500'))
				(cond
					((not (ego has iMoney))
						(Print "You have no money.")
					)
					((not nearSecretary)
						(NotClose)
					)
					((and	(!= egoState NORMAL)
							(!= egoState WEARING_ZIEGFIELD))
						(NotNow)
					)
					((!= SecretaryState NORMAL)
						(Print "The receptionist seems to be busy right now.")
					)
					((== lawyerState LSbusy)
						(User canInput FALSE)
						(Format @string "\"That won't help, sir.
							There are simply no lawyers available at this time.
							Why don't you come back later?\"")
						(self changeState startTalking)
					)
					((== lawyerState LSfree)
						(User canInput FALSE)
						(Format @string "\"That's not necessary, sir.
							Ms. Cheatem, one of our finest attorneys,
							is available right now,
							and remember:
							here at Dewey, Cheatem and Howe your first consultation
							is always free of charge.\"")
						(self changeState startTalking)
					)
					((> lawyerState LSneeds500)
						(User canInput FALSE)
						(Format @string "\"You've paid all you owe.
							No more money is necessary, Mr. Laffer.\"")
						(self changeState startTalking)
					)
					((!= dollars 500)
						(Print "Too bad, Larry.
							Ms. Cheatem instructed you to bring $500.00 with you
							to pay for your next visit, remember?
							Check your pockets, but you don't have it.")
						(Print "(Yet.)" LOW)
					)
					(else
						(Ok)
						(= lawyerState LSdivorce)
						(= dollars 0)
						(ego put iMoney LIMBO)
						(User canInput FALSE)
						(Print "\"Here's the five hundred dollars Ms. Cheatem
							requested,\" you tell the receptionist.
							\"May I please meet with her to finalize my divorce now?\""
								#icon		ivOnes 0 0
						)
						(Format @string "\"Why, certainly, Mr. Laffer,\"
							replies the receptionist,
							\"walk right in.
							Ms. Cheatem will be happy to talk with you now.\"")
						(RoomScript changeState goSeeSuzi)
						(self changeState startTalking)
					)
				)
			)

			((and (ego has iDivorce)
					(not (ego has iKeycard))
					(Said 'look, read/divorce, paper, document'))
				(event claimed FALSE)		;**	let 000 handle it
				(return)
			)

			((or	(Said '/divorce, paper, (paper<divorce)')
					(Said '//divorce, paper, (paper<divorce)'))
				(cond
					((not nearSecretary)
						(NotClose)
					)
					((and	(!= egoState NORMAL)
							(!= egoState WEARING_ZIEGFIELD))
						(NotNow)
					)
					((!= SecretaryState NORMAL)
						(Print "The receptionist seems to be busy right now.")
					)
					((== lawyerState LSbusy)
						(User canInput FALSE)
						(Printf "\"Excuse me,\" you say,
							interrupting the man working at the counter.
							%s" laffer)
						(Print "\"I need a divorce.
							Is there anyone here that could help me?\"")
						(Format @string "\"Good day, sir.
							Welcome to the legal offices of Dewey, Cheatem and Howe.
							You've come to the right place, but unfortunately,
							all of our attorneys are busy right now.
							Could you please return later when one of them is free?\"")
						(self changeState startTalking)
					)
					((== lawyerState LSfree)
						(User canInput FALSE)
						(Printf "\"Excuse me,\" you say,
							interrupting the man working at the counter.
					  		%s" laffer)
						(Print "\"I'm in desperate need of a divorce.
							Is there anyone here that could help me?\"")
						(Format @string "\"Hello, Mr. Laffer.
							It's a pleasure to meet you.
							Certainly, we here at Dewey, Cheatem and Howe
							would be most proud to satisfy all your legal needs.
							Currently, Mr. Dewey is in court, and Mr. Howe is
							taking depositions down at the court hut,
							but you are in luck: Ms. Cheatem is available.
							Go right on in; her door is just behind me.
							I'll tell her you're on your way in.\"")
						(RoomScript changeState goSeeSuzi)
						(self changeState startTalking)
					)
					((== lawyerState LSwaiting4deed)
						(User canInput FALSE)
						(Print "\"How can I get a divorce around here?\" 
							you ask the clerk.")
						(Format @string "\"Ms. Cheatem has given me complete
							instructions for the preparation of your land deed,
							but she mentioned nothing about a divorce.
							Have you paid your fee yet, Mr. Laffer?\"")
						(self changeState startTalking)
					)
					(( < lawyerState LSwaiting4divorce)
						(User canInput FALSE)
						(Print "\"Roger, I'd like to get this divorce business
							all straightened out.
							When could I meet with Ms. Cheatem again?\" you ask.")
						(Format @string "\"You've already had your one (and only)
							free introductory consultation, Mr. Laffer.
							Suzi will be happy to meet with you again,
							but to complete your legal arrangements,
							you must pay me your $500.00 fee.\"")
						(self changeState startTalking)
					)
					(( < lawyerState LSdivorceReady)
						(User canInput FALSE)
						(Print "\"Now may I get my divorce papers?\" you ask Roger.")
						(Format @string "\"Geez, Larry.
							Give me a little time, will you?
							Your papers will be ready just as soon as
							I can prepare them.\"")
						(self changeState startTalking)
					)
					(( > lawyerState LSdivorceReady)
						(User canInput FALSE)
						(Print "\"It's nice to see you again, Roger.\"")
						(Format @string "\"So long, Larry!\"")
						(self changeState startTalking)
					)
					(else							;**	The divorce is here to pick up.
						(User canInput FALSE)
						(Print "\"Aren't those divorce papers ready yet?\"
							you ask Roger.")
						(Format @string "\"I'm glad you asked, Mr. Laffer!
							They're all set.
							Here you are.
							And, Ms. Cheatem told me to tell you
							it was a %s with you!\""
							(if (TstFlg scoredSuzi)
								{business doing pleasure}
							else
								{pleasure doing business}
							)
						)
						(= lawyerState LSdone)
						(ego get iDivorce)
						(theGame changeScore 20)
						(self changeState startTalking)
					)
				)
			)

			((and (ego has iDeed)
					(Said 'look/deed, land'))
				(event claimed FALSE)		;**	let 000 show it
				(return)
			)

			((or	(Said '//deed, land')
					(Said '/deed, land'))
				(cond
					((not nearSecretary)
						(NotClose)
					)
					((and	(!= egoState NORMAL)
							(!= egoState WEARING_ZIEGFIELD))
						(NotNow)
					)
					((!= SecretaryState NORMAL)
						(Print "The receptionist seems to be busy right now.")
					)
					((== lawyerState LSbusy)
						(User canInput FALSE)
						(Printf "\"Excuse me,\" you say,
							interrupting the man working at the counter.
							%s" laffer)
						(Print "\"It is my understanding that island custom
							dictates automatic transference of ownership of any
							lands in a couple's possession to the husband upon
							a divorce.
							I'd like to begin such a proceeding.
							Is there anyone here that could help me?\"")
						(Format @string "\"Good day, sir.
							Certainly, you've made the right choice by coming to
							Dewey, Cheatem and Howe!
							Unfortunately, all of our attorneys are busy at this time.
							Could you please return in a few minutes?\"")
						(self changeState startTalking)
					)
					((== lawyerState LSfree)
						(User canInput FALSE)
						(Printf "\"Excuse me,\" you say,
							interrupting the man working at the counter.
							%s" laffer)
						(Print "\"I'd like to talk to one of your attorneys about
							a real estate ownership transfer.
							Is there anyone available to help me?\"")
						(Format @string "\"Good day, Mr. Laffer.
							Certainly Dewey, Cheatem and Howe is the right place
							for all your legal needs.
							At this time, Mr. Howe is taking depositions at the
							court hut and Mr. Dewey is tied up in court.
							However, Ms. Cheatem is readily available.
							Just walk through the door immediately behind me.
							I'll tell her you're here.\"")
						(RoomScript changeState goSeeSuzi)
						(self changeState startTalking)
					)
					(( < lawyerState LSdeedReady)
						(User canInput FALSE)
						(Print "\"Is that deed ready?\" you ask the clerk.")
						(Format @string "\"Ms. Cheatem has instructed me to
							prepare your land deed.
							I'll have it ready soon.
							Why don't you come back in a few minutes, Mr. Laffer.\"")
						(self changeState startTalking)
					)
					(( > lawyerState LSdeedReady)
						(User canInput FALSE)
						(Print "\"Thanks a lot for that deed, Roger.\"")
						(Format @string "\"You're welcome, Mr. Laffer.
							But I bet you didn't come here just to exchange
							pleasantries with the hired help!
							How may I help you this time?\"")
						(self changeState startTalking)
					)
					(else
						(User canInput FALSE)
						(Print "\"Is that land deed ready yet?\"
							you ask the clerk.")
						(Format @string "\"Absolutely, Mr. Laffer.
							Here it is.
							But, you know that land is so isolated from the main
							tourist areas you'll never be able to sell it
							to a developer.
							It's nothing but worthless forest land!
							No one could build a tourist trap there!!\"")
						(= lawyerState LSneeds500)
						(ego get iDeed)
						(theGame changeScore 20)
						(self changeState startTalking)
					)
				)
			)
		)
	)


	(method (changeState newState)
		(ShowState self newState 2 green)

		(switch (= state newState)

			(SecretaryInit
				(aRoger
					cycleSpeed 		0,
					loop				lWriting,
					setCel			0,
					setCycle			FALSE,
				)
				(= SecretaryState NORMAL)
				(= cycles 0)					;**	just in case
				(delay (Random 3 6))
			)

			(whatWillHeDoNext
				(if (== SecretaryState NORMAL)
					(= SecretaryState (Random WRITING (+ WRITING 7)))
				)
				(cond
					((!= string 0)						(self changeState startTalking))
					((== SecretaryState WRITING)	(self changeState startWriting))
					((== SecretaryState YAWNING)	(self changeState startYawning))
					((== SecretaryState PHONING)	(self changeState startPhone))
					(else									(self changeState SecretaryInit))
				)
			)


			;**	Talk to Larry

			(startTalking
				(= SecretaryState TALKING)
				(aRoger
					loop				lTalking,
					setCycle			Forward,
				)
				(= cycles (Random 11 44))
			)

			(sayWhatYouMean
				(if (== string 0)
					(switch (Random 1 5)
						(1	(Format @string
							"\"What are you waiting for? Go on in!\"")
						)
						(2	(Format @string
							"\"Don't you see I'm VERY busy!\"")
						)
						(3 (Format @string
							"\"Did I mention that I've been seeing quite
							a bit of Passionate Patti?\"")
						)
						(4 (Format @string
							"He ignores you and returns to work.")
						)
						(5 (Format @string
							"\"Can't you see I'm busy!\"")
						)
					)
				)
				(Print @string
					HIGH
					#title		{Roger says}
					#mode			teJustCenter
					#icon			vSecretary lFaceIcon 0
				)

				(User canInput TRUE)
				(if (== (RoomScript state) goSeeSuzi)
					(RoomScript cue)
				)

				(= string 0)
				(= cycles 22)
				(= state readyToStartOver)
			)


			;**	Make him write something...

			(startWriting
				(aRoger
					loop				lWriting,
					setCycle 		Forward,
					cycleSpeed 		1,
				)
				(= cycles (Random 9 19))
			)

			(stopIt
				(aRoger
					setCel	 		0,
				)
				(= cycles (Random 9 19))
				(if (Random 0 2)				;**	Go write some more
					(= state (- startWriting 1))
				else
					(= state readyToStartOver)
				)
			)


			;**	Wake up, Roger!

			(startYawning
				(aRoger
					cycleSpeed		2,
					loop				lYawning,
					cel				0,
					setCycle			EndLoop self,
				)
			)

			(yawnAlittle
				(= cycles (Random 9 19))
			)

			(stopYawning
				(aRoger
					setCycle			BegLoop self,
				)
				(= state readyToStartOver)
			)


			;**	Hey, Roger! It's for you!!

			(startPhone
				(aRoger
					cycleSpeed		1,
					loop				lPhoneToEar,
					cel				0,
					setCycle			EndLoop self,
				)
			)

			(chatAlittle
				(aRoger
					cycleSpeed		1,
					loop				lTalkOnPhone,
					setCycle			Forward,
				)
				(= cycles (Random 9 19))
			)

			(chatAlittleMore
				(aRoger
					setCel	 		0,
				)
				(= cycles (Random 9 19))
				(if (Random 0 3)				;**	Talk some more?
					(= state (- chatAlittle 1))
				)
			)

			(putPhoneDown
				(aRoger
					loop				lPhoneToEar,
					setCel			LastCel,
					setCycle			BegLoop self,
				)
				(= state readyToStartOver)
			)
		)
 	)
)


(instance atpSign of PicView
	(properties
		view					vRoom
		loop					lSign
		x						151
		y						125
		priority				9
		illegalBits			0
		signal				ignrAct
	)

)
(instance atpDCHSign of PicView
	(properties
		view					vRoom
		loop					lDCHSign
		x						151
		y						93
		priority				9
		signal				ignrAct
	)
)
(instance atpDeskStuff of PicView
	(properties
		view					vRoom
		loop					lDeskStuff
;		cel					cComputer
		x						165
		y						141
		priority				12
	)
)
(instance atpPencils of PicView
	(properties
		view					vRoom
		loop					lDeskStuff
		cel					cPencils
		x						105
		y						136
		priority				12
	)
)
(instance atpFax of PicView
	(properties
		view					vRoom
		loop					lFaxMachine
;		cel					0
		x						106
		y						142
		priority				12
		signal				ignrAct
	)
)

(instance aFax of Extra
	(properties
		view					vRoom
		loop					lFax
;		cel					0
		x						106
		y						133
		cycleSpeed			1
	)

	(method (init)
		(super init)
		(self
			cycleType		ExtraEndLoop,
			pauseCel			ExtraLastCel,
			minPause			99,
			maxPause			999,
			setPri			12,
			isExtra			TRUE,
			ignoreActors,
			startExtra,
		)
	)
)

(instance aRoger of Prop
	(properties
		view					vSecretary
		loop					lTalking
		x	 					146
		y						130
	)
	(method (init)
		(super init)
		(self
			setPri			12,
			setScript		SecretaryScript,
		)
	)
)

(instance aDoor of AutoDoor
	(properties
		view					vRoom
		x						153
		y						39
		entranceTo 			323
	)
	(method (init)
		(super init)
		(self
			setLoop			3,
			setPri			5,
		)
	)
)
