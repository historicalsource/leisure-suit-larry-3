;**
;**	Logics for room 323 -- Suzi Cheatem's office
;**
;**	LSL3:  Passionate Patti in Pursuit of the Pulsating Pectorals
;**	       Copyright 1989
;**		by Al Lowe, Bill Skirvin, Carlos Escobar, and Roger Hardy
;**
;**	Last Update:	January 17, 1990
;**


(script#				323)

(public
	rm323				0
)


(include "323.sh")


(enum												;**	RoomScript state values
	intro
	helloI'm
	sitDown
	atSofa
	preFidgetDelay
	fidget
	postFidgetDelay
	fidget2
	standUp
	standing
)


(enum												;**	SuziScript state values
	suziInit
	whatWillSheDoNext
	startTalking
	sayWhatYouMean
	startWriting
	stopWriting
	startFax
	faxDelay
	standSuziUp
	walk1
	walk2
	atFaxMachine
	doneWithFax
	goBackToChair
	goAroundTable
	goToChair
	startSkirt
	doneTugging
	startPhone
	talkAlittle
	hangUpPhone
	phoneDone
)


(enum												;**	ChairScript state values
	chairInit
	shallWeWiggle
	wiggleLegs
	stopWiggling
	makeAchair
	tugSkirt
)


(local
	[string 300]
	seenMsg
	SuziState
		(enum 1									;**	Local SuziState values
			WRITING
			TALKING_TO_LARRY
			TALKING_ON_PHONE
			NOT_SEATED
			WALKING_TO_FAX
			WALKING_FROM_FAX
			TUGGING_SKIRT
		)
)


(enum 323										;**	egoState values
	SITTING_ON_SOFA
)


(synonyms
	(girl lawyer )
)


(instance rm323 of Room
	(properties
		picture		323
		west	 		320
	)


	(method (init)
		(Load VIEW vLsitting323)
		(Load VIEW vSuzi)

		(Load SOUND sPhone)

		(super init)

		(addToPics
			add					atpProps,
			add					atpDoorNorth,
			add					atpDoorSouth,
			doit,
		)

		(aSuzi  					init)
		(aChair 					init)

		(self
			setScript 			RoomScript
		)

		(= string 0)

		(if (!= prevRoomNum 325)
			(= prevRoomX 41)
			(= prevRoomY 156)
			(= prevLoop  facingEast)
		)
		(if (== egoState SITTING_ON_SOFA)
			(HandsOff)
			(ego
				view				vLsitting323,
				setCel			LastCel,
			)
			(= prevLoop lSittingCouch)
			(RoomScript changeState preFidgetDelay)
		else
			(NormalEgo)
		)
		(ego
			posn					prevRoomX prevRoomY,
			loop					prevLoop,
			observeBlocks		blockOne blockTwo wallBlockLeft wallBlockRight,
			init,
		)
	)
)


(instance RoomScript of Script
	(method (doit)
		(super doit)

		(if nearDoor
			(cond
				((== lawyerState LSfree)
					(++ lawyerState)			;**	(= lawyerState LSwaiting4deed)
					(Print "\"Oh, by the way, Mr. Laffer,\" says Ms. Cheatem,
						\"according to an ancient island custom,
						any land owned by either or both spouses
						upon dissolution of the marriage
						becomes the exclusive property of the male
						member of the household.\""
							#icon			vSuziSitting lFaceIcon 0
						)
					(Print "\"Since Kalalau was the daughter of the tribal chief,
						and her dowry contained sizable quantities of real estate,
						this means you are now the owner of a considerable
						chunk of real estate.
						I'll make the necessary arrangements and have
						Roger, my secretary, draw up the papers for you.\""
							#icon			vSuziSitting lFaceIcon 0
						)
					(Print "\"Stop by the next time you're in the neighborhood;
						they shouldn't take long to prepare.\""
							#icon			vSuziSitting lFaceIcon 0
						)
				)
				((== lawyerState LSdivorce)
					(++ lawyerState)			;**	(= lawyerState LSwaiting4divorce)
					(Print "\"Oh, by the way, Mr. Laffer,\" says Ms. Cheatem,
						\"the grapevine has it that you are interested in
						leaving your wife.
						Since you did pay Roger your $500.00,
						I'll be happy to draw up your divorce for you.\""
							#icon			vSuziSitting lFaceIcon 0
						)
					(Print "\"Stop by the next time you're in the neighborhood;
						it shouldn't take long to prepare.\""
							#icon			vSuziSitting lFaceIcon 0
						)
				)
			)
			(curRoom newRoom 320)
		)
	)


	(method (handleEvent event)
		(if (or	(!= (event type) saidEvent) (event claimed)) (return))

		(cond
			((Said 'sit')
				(cond
					((not nearSofa)
						(Print "Walk over until you're near the front of the sofa.")
					)
					((== egoState SITTING)
						(YouAre)
					)
					((!= egoState NORMAL)
						(NotNow)
					)
					(else
						(self changeState sitDown)
					)
				)
			)

			((or	(Said 'rise, (get<off), (get<up), (stand<up)')
					(Said 'leave/sofa, chair'))
				(cond
					((== egoState NORMAL)
						(YouAre)
					)
					((!= egoState SITTING)
						(Print "How can you do that?")
					)
					(else
						(self changeState standUp)
					)
				)
			)

			((Said 'talk/suzi')
				(cond
					((!= egoState SITTING)
					 	(Print "\"Please, have a seat on the sofa, Mr. Laffer,\"
							says Ms. Cheatem.")
					)
					((!= SuziState NORMAL)
						(Print "Ms. Cheatem appears to be busy right now.")
					)
					((== lawyerState LSfree)
						(Printf "\"Hello, Ms. Cheatem,\" you tell the lawyer.
							%s" laffer)
						(Format @string "\"Good day, Mr. Laffer.
							How may I help you?\"")
						(SuziScript changeState startTalking)
					)
					(else
						(Print "\"Nice day, isn't it, Ms. Cheatem?\" you ask.")
						(Format @string "\"What? 
							You want me to make small talk at $500.00 an hour?\"")
						(SuziScript changeState startTalking)
					)
				)
			)

			((or	(Said '/deed, land')
					(Said '//deed, land'))
				(cond
					((!= egoState SITTING)
						(Print "\"Please have a seat, Mr. Laffer!\"
							says Ms. Cheatem.")
					)
					((!= SuziState NORMAL)
						(Print "She seems to be busy right now.")
					)
					((== lawyerState LSfree)
						(++ lawyerState)		;**	(= lawyerState LSwaiting4deed)
						(theGame changeScore 30)
						(Print "\"Ms. Cheatem, allow me to come right to the point.
							My wife, Kalalau, was a wonderful woman and brought
							to our marriage a considerable dowry,
							consisting of a few no-load mutual funds
							and some extensive real estate holdings.\"")
						(Print "\"I was wondering if there was any easy way for
							me to get my hands on that land.
							I've heard of some ancient island tradition concerning
							men and real property...\"")
						(Format @string "\"Say no more, Mr. Laffer.
							I know exactly what you mean.
							According to ancient island custom,
							any land owned by either or both spouses
							upon dissolution of the marriage
							becomes the exclusive property of the male
							member of the household.
							Congratulations, Mr. Laffer.
							You are now the owner of a considerable chunk of
							Nontoonyt real estate.
							I'll make the necessary arrangements and have
							Roger, my secretary, boilerplate the papers for you.\"")
						(SuziScript changeState startTalking)
					)
					((== lawyerState LSwaiting4deed)
						(Format @string "\"Thank you very much for stopping by,
							Mr. Laffer.
							Your free consultation has officially ended.\"")
						(SuziScript changeState startTalking)
					)	
					(else
						(Print "\"Nice day, isn't it, Ms. Cheatem?\" you ask.")
						(Format @string "\"What? 
							You want me to make small talk at $500.00 an hour?\"")
						(SuziScript changeState startTalking)
					)
				)
			)

			((or	(Said '/divorce')
					(Said '//divorce'))
				(cond
					((!= egoState SITTING)
						(Print "\"Please have a seat, Mr. Laffer!\"")
					)
					((!= SuziState NORMAL)
						(Print "She seems to be busy right now.")
					)
					((or	(== lawyerState LSfree)
							(== lawyerState LSwaiting4deed))
						(Print "\"How exactly do I go about getting a divorce,
							Ms. Cheatem?\"")
						(Format @string "\"It's simple, Mr. Laffer.
							As long as you don't expect to take your case through
							the local court system (which is owned lock,
							stock and barrel by Kalalau's father)
							you can just let me take care of everything.
							My standard divorce runs $500.00, and of course,
							I expect to be paid in cash.\"")
						(SuziScript changeState startTalking)
					)
					((== lawyerState LSdivorce)
						(++ lawyerState)		;**	(= lawyerState LSwaiting4divorce)
						(theGame changeScore 40)
						(Print "\"Isn't it time we finalized my divorce,
							Ms. Cheatem?\"")
						(Format @string "\"Absolutely, Larry.
							Since you paid your $500.00 to Roger,
							I'll get started on it right away.
							The next time you're in the neighborhood,
							stop by his desk.
							It's a fairly straightforward agreement;
							it shouldn't take long to prepare.\"")
						(SuziScript changeState startTalking)
					)
					((== lawyerState LSwaiting4divorce)
						(Print "\"I was too stupid to listen the first time you
							told me about the divorce.
							Would you explain it all again, Ms. Cheatem?\"")
						(Format @string "\"Absolutely not, Larry.
							What do you expect for only $500.00?\"")
						(SuziScript changeState startTalking)
					)
					(else
						(Format @string "\"Thank you very much for stopping by,
							Mr. Laffer.
							Your free consultation is now over.\"")
						(SuziScript changeState startTalking)
					)
				)
			)

			((Said '/fax')
				(Print "It's for internal use only.")
				(Print "(Corporate internals, that is!)" LOW)
			)

			((Said '/telephone')
				(Print "There is a telephone on the desk,
					but don't get any cute ideas about using it!")
			)

			((Said '/desk, leg')
				(Print "Why look! You can see right through her desktop
					as if it were glass!")
			)

			((Said '/sofa')
				(Print "It looks comfortable.")
			)

			((Said '/picture')
				(Print "The picture is beautiful, but you're more interested
					in other vistas hereabouts.")
			)

			((Said '/door')
				(Print "The doors are open and Ms. Cheatem expects them to
					stay that way!")
			)

			((Said '/window')
				(Print "Ms. Cheatem's glass-topped desk sits before her
					mini-blinded window overlooking the lovely
					toxic-waste and hyphen-removal plant.")
			)

			((Said '/shelf, globe, shelf, cabinet, book')
				(Print "Her shelves are filled with leather-bound legal tomes
					and collections of \"TV Guide.\"")
			)

			((Said 'look>')
				(cond
					((Said '/girl, face')
						(switch SuziState
							(WRITING
								(Print "Ms. Cheatem must feel whatever it is
									she's writing is more important than talking
									to you.")
							)
							(TALKING_ON_PHONE
								(Print "Ms. Cheatem is busy right now,
									talking to a bigger name on line 3.")
							)
							(WALKING_TO_FAX
								(Print "How rude!
									She got up and walked away from her desk right
									in the middle of your stimulating conversation.")
							)
							(WALKING_FROM_FAX
								(Print "Ms. Cheatem must have had an important
									incoming fax, since she had to interrupt your
									meeting to pick it up from the fax machine.")
							)
							(else
								(Ok)
								(= prevRoomX (ego x))
								(= prevRoomY (ego y))
								(= prevLoop  (ego loop))
								(if (== egoState SITTING)
									(= egoState SITTING_ON_SOFA)
								)
								(curRoom newRoom 325)
							)
						)
					)

					((Said '[/office, room]')
						(Print "Ms. Cheatem has a nice office
							with a large picture window, much nicer than
							that cheap cubicle Chairman Kenneth
							gave you at Natives, Inc!")
					)
				)
			)
		)
	)


	(method (changeState newState)
		(ShowState self newState 1 green)

		(switch (= state newState)

			(intro
				(if (not (TstFlg beenIn323))
					(delay 3)
				)
			)

			(helloI'm
				(SetFlg beenIn323)
				(Print "\"Good day, Mr. Laffer,\" says the attorney from
					her desk with a smile, \"I'm Suzi Cheatem.
					How may I help you?\"")
				(Print "\"Please, make yourself at home. 
					Have a seat on my couch.\"")
				
			)

			;**	Sit down as a normal-type Larry

			(sitDown
				(HandsOff)
				(Ok)
				(ego illegalBits 0)
				(cond								;**	move to either end.
					(( > (ego x) 119)
						(ego setMotion MoveTo 119 132 self)
					)
					(( < (ego x) 90)
						(ego setMotion MoveTo 90 132 self)
					)
					(else
						(ego setMotion MoveTo (ego x) 132 self)
					)
				)
			)

			(atSofa
				(ego
					view				vLsitting323,
					loop				lSittingCouch,
					cel				0,
					setCycle			EndLoop self,
				)
			)

			(preFidgetDelay
				(= egoState SITTING)
				(User canInput TRUE)
				(= cycles (Random 33 66))
			)

			(fidget
				(ego
					setLoop			(Random lYawning lStretching), ;**	loops 1-4
					cel				0,
					setCycle			EndLoop self,
				)
			)

			(postFidgetDelay
				(= cycles (Random 11 33))
			)

			(fidget2
				(ego
					setCycle			BegLoop self,
				)
				(= state (- preFidgetDelay 1))
			)


			;**	LARRY STANDS UP

			(standUp
				(HandsOff)
				(Ok)
				(ego
					setLoop			lSittingCouch,
					setCel			LastCel,
					setCycle			BegLoop self,
				)
			)

			(standing
				(NormalEgo facingDown)
				(= egoState NORMAL)
			)
		)
	)
)


(instance SuziScript of Script
	(method (changeState newState)
		(ShowState self newState 2 blue)

		(switch (= state newState)

			(suziInit
				(aSuzi
					loop				lTalking,
					setCel			0,
					setCycle			FALSE,
				)
				(= SuziState NORMAL)
				(ChairScript changeState wiggleLegs)
				(= cycles 0)					;**	just in case
				(delay (Random 5 10))
			)

			(whatWillSheDoNext
				(= SuziState (Random 0 7))
				(cond
					((== SuziState WRITING)				(self changeState startWriting))
					((== SuziState TALKING_ON_PHONE)	(self changeState startPhone))
					((== SuziState WALKING_TO_FAX)	(self changeState startFax))
					((== SuziState TUGGING_SKIRT)		(self changeState startSkirt))
					(else										(self changeState suziInit))
				)
			)


			;**	Talk to Larry

			(startTalking
				(= SuziState TALKING_TO_LARRY)
				(aSuzi
					view				vSuziSitting,
					loop				lTalking,
					setCycle			Forward,
				)
				(= cycles (Random 11 44))
			)

			(sayWhatYouMean
				(if (== string 0)
					(if (== lawyerState LSfree)
						(Format @string "\"Well,
							I've certainly enjoyed our little visit together.
							When you're ready to do business,
							please come back and see me again.\"")
					else							;**	(== lawyerState LSdivorce)
						(Format @string "\"It was so nice of you to stop by again,
							Mr. Laffer.
							Please come see us again,
							whenever you have legal needs to be fulfilled!\"")
					)
				)
				(Print @string
					HIGH
					#title		{Suzi says}
					#mode			teJustCenter
					#icon			vSuziSitting lFaceIcon 0
				)
				(if (and	(== lawyerState LSfree)
							(not seenMsg))
					(= seenMsg TRUE)
					(Print "\"Oh, by the way, Mr. Laffer,
						did my secretary explain to you my policy concerning
						new clients?
						This first consultation is always free,
						but then you'll owe me for any work I do.
						I'm sure you understand.\""
						HIGH
						#title		{Suzi says}
						#mode			teJustCenter
						#icon			vSuziSitting lFaceIcon 0
					)
				)
				(= string 0)
				(= cycles 22)
				(= state readyToStartOver)
			)


			;**	WRITE A LITTLE

			(startWriting
				(aSuzi
					view				vSuziSitting,
					loop				lWriting,
					setCycle			Forward,
				)
				(delay 3)
			)

			(stopWriting
				(self changeState suziInit)
				(ChairScript cue)
			)


			;**	Walk to fax machine

			(startFax
				(= SuziState NORMAL)			;**	for a little while
				(delay 2)
			)

			(standSuziUp
				(= SuziState WALKING_TO_FAX)
				(aSuzi
					view				vSuzi,
					loop				facingNorth,
					illegalBits		0,
					ignoreActors	FALSE,
					setPri			RELEASE,
					loop				RELEASE,
					setCycle			Walk,
					setMotion		MoveTo deskUR_X deskUR_Y self,
				)
				(ChairScript changeState makeAchair)
			)

			(walk1
				(aSuzi
					setMotion		MoveTo deskUL_X deskUL_Y self,
				)
			)

			(walk2
				(aSuzi
					setMotion		MoveTo faxX faxY self,
				)
			)

			(atFaxMachine
				(= cycles 15)
			)

			(doneWithFax
				(= SuziState WALKING_FROM_FAX)
				(aSuzi
					loop				RELEASE,
					setCycle			Walk,
					setMotion		MoveTo deskLL_X deskLL_Y self,
				)
			)

			(goBackToChair
				(aSuzi
					setMotion		MoveTo deskUL_X deskUL_Y self,
				)
			)

			(goAroundTable
				(aSuzi
					ignoreActors	TRUE,
				  	setMotion		MoveTo deskUR_X deskUR_Y self,
				)
			)

			(goToChair
				(aSuzi
 				  	setMotion		MoveTo 238 130 self,
 				)
 			)


			;**	Pull down your dress, Suzi!

			(startSkirt
				(aSuzi
					view				vSuziSitting,
					posn				SuziX SuziY,
					loop				lTuggingSkirt,
					cel				0,
					setPri			ltMagenta,
					setCycle			EndLoop self,
				)
				(ChairScript changeState tugSkirt)
			)

			(doneTugging
				(ChairScript changeState chairInit)
				(self changeState suziInit)
			)


			;**	Pick up phone

			(startPhone
				(aSuzi
					view				vSuziSitting,
					loop				lGetPhone,
					cel				0,
					setCycle			EndLoop self,
				)
				(ChairScript changeState wiggleLegs)
			)

			(talkAlittle
				(aSuzi
					loop				lTalkPhone,
					setCycle			Forward,
				)
				(= cycles (Random 11 33))
			)

			(hangUpPhone
				(aSuzi
					loop				lGetPhone,
					setCel			LastCel,
					setCycle			BegLoop self,
				)
			)

			(phoneDone
				(ChairScript changeState chairInit)
				(self changeState suziInit)
			)
		)
	)
)

(instance ChairScript of Script
	(method (changeState newState)
		(ShowState self newState 3 red)

		(switch (= state newState)

			(chairInit
				(delay (Random 5 10))
			)

			(shallWeWiggle
				(if ( < SuziState NOT_SEATED)
					(self cue)
				else
					(self changeState chairInit)
				)
			)

			(wiggleLegs
				(aChair
					loop				lWiggleLegs,
					cycleSpeed		2,
					setCycle			Forward,
				)
				(delay (Random 2 5))
			)

			(stopWiggling
				(aChair
					setCel			0,
					setCycle			FALSE,
				)
				(self changeState chairInit)
			)

			(makeAchair
				(aChair
					view				vSuziChair,
					posn				chairX chairY,
					loop				lChair,
					stopUpd,
				)
				(delay 0)
				(= cycles 0)
			)

			(tugSkirt
				(aChair
					view				vSuziChair,
					loop				lTuggingSkirt,
					posn				chairX chairY,
					cel				0,
					setCycle			EndLoop ,
				)
			)
		)
	)
)


(instance atpProps of PicView
	(properties
		view				vSuziChair
		loop				lDesktopStuff
		x					240
		y					132
		priority			10
		signal			ignrAct
	)
)
(instance aChair of Prop
	(properties
		view 			vSuziChair
		loop			lWiggleLegs
		x				chairX
		y				chairY
		signal		ignrAct
	)
	(method (init)
		(super init)
		(self
			setPri		ltBlue,
		)
	)
)

(instance aSuzi of Actor
	(properties
		view					vSuziSitting
		loop					lGetPhone
		x						SuziX
		y						SuziY
	)

	(method (init)
		(super init)
		(self
			ignoreActors,
			illegalBits		0,
			setScript		SuziScript,
			setPri			ltCyan,			;**	ltMagenta,
			stopUpd,
		)
	)
)

(instance blockOne of Block
	(properties
		top				199
		bottom			333
		left				74
		right				231
	)
)
(instance blockTwo of Block
	(properties
		top				217
		bottom			333
		left				-20
		right				333
	)
)
(instance wallBlockLeft of Block
	(properties
		top				175
		bottom			333
		left				-20
		right				-3
	)
)
(instance wallBlockRight of Block
	(properties
		top				0
		bottom			333
		left				325
		right				340
	)
)

(instance aPhone of Actor
	(properties
		illegalBits		0
		view 				vSuziOffice
;		cel				0
		x					TrapdoorX
		y					-4
	)
	(method (init)
		(super init)
		(self
			ignoreHorizon,
			ignoreActors,
			loop				lTrapdoor,
			setPri			magenta,
			setStep			1 1,
		)
	)
)

(instance atpDoorSouth of PicView
	(properties
		view 				vSuziOffice
;		loop				lLowerDoor
		cel				3						;**	doors open
		x	 				20
		y					103
		priority			13
		signal			ignrAct
	)
)
(instance atpDoorNorth of PicView
	(properties
		view 				vSuziOffice
		loop				lUpperDoor
		cel				3
		x	 				41
		y					95
		priority			11
		signal			ignrAct
	)
)
