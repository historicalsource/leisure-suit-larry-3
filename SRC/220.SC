;**
;**	Logics for room 220 -- Outside the Attorney's Office
;**
;**	LSL3:  Passionate Patti in Pursuit of the Pulsating Pectorals
;**	       Copyright 1989
;**		by Al Lowe, Bill Skirvin, Carlos Escobar, and Roger Hardy
;**
;**	Last Update:	October 24, 1989
;**


(script#				220)

(public
	rm220				0
)


(procedure
	PrintPlot
)


(define	vRoom							220)
	(define	lGraphicsBy			0)
	(define	lWilliam				1)
	(define	lRoger				2)

(define	vLroom			(+ 1 vRoom))
	(define	lBooth				0)

(enum												;**	RoomScript state values
	intro
	whatDoWeDoThisTime
	learnThePlot
	p1b
	plot2
	plot3
	plot4
	plot4b
	plot4c
	plot5
	p5a
	plot5b
	plot6
	plot6and7-8ths
	LdoesTake1
	LdoesTake2
	gotoBooth
	enterBooth
	shakeBooth
	shakeAgain
	nextLoop2
	andShakeAgain
	leaveBooth
	waitAsec
	lowerBooth
	plot7
	plotDone
	flushBooth
	readyToPlayGame
)


(enum												;**	CreditsScript state values
	doCredits
	doCredits1
	doCredits2
	doCredits3
	doCredits4
	doCredits5
	clearCredits
)


(local
	seenMsg
	[string 222]
)


(instance rm220 of Room
	(properties
		picture		220
		horizon		67
		north			310
	)


	(method (init)

		(super init)

		(self
			setScript 		RoomScript
		)

		(if (and (TstFlg beenIn220)
					(not (TstFlg sawCredits220)))
			(Load VIEW vRoom)
			(Load VIEW vLroom)
			(Load FONT MASH_18)
			(Load SOUND sFanfare)

			(aCredit1			init)
			(aCredit2			init)
		)

		(cond
			((== prevRoomNum 300)			(ego posn	6 175))
			((== prevRoomNum 210)		 	(ego posn   2 151))
			((== prevRoomNum 310)		 	(ego posn 316  70))
			((== prevRoomNum 230)		 	(ego posn 316 142))
			(else								 	(ego posn 316 182))
		)

		(NormalEgo)
		(ego					init)
	)
)


(instance RoomScript of Script
	(method (doit)
		(super doit)

		(if (ego edgeHit)
			(cond
				((& (ego onControl) cBlue)		(curRoom newRoom 300))
				((& (ego onControl) cGreen)	(curRoom newRoom 210))
				((& (ego onControl) cCyan)		(curRoom newRoom 310))
				((& (ego onControl) cRed)		(curRoom newRoom 230))
				((& (ego onControl) cMagenta)	(curRoom newRoom 250))
			)
		)
;**	
;**			(if (and (& (ego onControl) cBlue)
;**						(!= egoState NORMAL))
;**				(if (not seenMsg)
;**					(= seenMsg TRUE)
;**					(ego
;**						posn 				(ego xLast) (ego yLast),
;**						setMotion		FALSE,
;**						observeControl cBlue,
;**						forceUpd,
;**					)
;**					(Print "You can't enter \"Fat City\" dressed like that!")
;**				)
;**			else
;**				(= seenMsg FALSE)
;**			)
	)


	(method (handleEvent event)
		(if (and (not (super handleEvent: event))
					(not (event claimed))
					modelessDialog
					(== (event message) ENTER)
					(== (event type) keyDown))
			(event claimed TRUE)
			(cls)
			(self cue)
		)

		(if (or	(!= (event type) saidEvent) (event claimed)) (return))

		(if (Said 'look>')
			(if (Said '/building, office, spa')
				(Print "It's difficult to see from here.
					Why not just walk inside?")
			)

			(if (Said '[/room]')
				(Print "Through the thick jungle you can barely see
					an office building to the northeast
					and a strange large blue structure to the southwest.")
			)
		)
	)


	(method (changeState newState)
		(switch (= state newState)

			(intro
				(= cycles 5)
			)

			(whatDoWeDoThisTime
				(cond
					((not (TstFlg beenIn216))
						(HandsOff)
						(Print "\"Say,\" you think,
							\"I bet Kalalau is at home right now,
							waiting eagerly for my return.
							I'll head back there right now!\"")
						(ego
							loop		facingWest,
						)
						(RedrawCast)
						(curRoom newRoom 210)
					)
					((not (TstFlg beenIn220))
						(HandsOff)
					 	(= cycles 25)
					)
				)
			)

			(learnThePlot
				(Format @string 
					"Recovering from Kalalau's shocking news,
					you carefully consider the alternatives."
				)	
				(ego
					setMotion		MoveTo 8 153 self,
				)
				(= seconds (PrintPlot))
			)

			(p1b
			)

			(plot2
				(Format @string 
					"\"I suppose I could go into mourning,\" you think.
					\"Mope around all day, sit in my room, rent lots of videos,
					things like that..."
				)	
				(= seconds (PrintPlot))
			)

			(plot3
				(Format @string 
					"\"Or, I could give up women,
					remain celibate forever, enter the ministry
					or something."
				)	
				(= seconds (PrintPlot))
			)

			(plot4
				(Format @string 
					"\"Wait a minute, what am I thinking?
					No way! Not me.
					Not Larry; Larry Laffer!"
				)	
				(= seconds (PrintPlot))
			)

			(plot4b
				(Format @string 
					"\"You know, this island is the perfect location
					for a sophisticated, single swinger like yours truly.")
				(= seconds (PrintPlot))
			)

			(plot4c
				(Format @string 
					"\"And, thanks to a wonderful island tradition,
					Kalalau's dowry gave me hundreds of acres of
					potentially valuable forest land.")	
				(= seconds (PrintPlot))
			)

			(plot5
				(Format @string 
					"\"Perhaps my love for Kalalau blinded me
					to the potential of my current locale.
					Where else could I find more women than at a tropical resort?
					And to think,
					every one of them came here seeking just one thing_--
					a good time!"
				)	
				(ego
					setMotion		MoveTo 85 153 self,
				)
				(= seconds (PrintPlot))
			)

			(p5a
			)

			(plot5b
				(Format @string 
					"\"Yeah, that's it!\" you exclaim out loud.
					\"I've had it with monogamy,
					marriage,
					long-term relationships,
					commitment."				;**	spellchecked June 15, 1989
				)	
				(music fade)
				(= seconds (PrintPlot))
			)

			(plot6
				(Format @string 
					"\"So be it!
					My life's new goal will be to allow as many women as
					possible to enjoy me while they can!\"")	
				(= seconds (PrintPlot))
			)

			(plot6and7-8ths
				(aBooth
					init,
					setMotion		MoveTo 111 153 self,
				)
				(if ( > machineSpeed pcAT)
					(music
						number			sFanfare,
						loop				1,
						play,
					)
				)
			)

			(LdoesTake1
				(ego
					loop			facingSouth,
				)
				(= cycles 22)
			)

			(LdoesTake2
				(ego
					loop			facingNorth,
				)
				(= cycles 22)
			)

			(gotoBooth
				(ego
					setPri			10,
					setMotion		MoveTo 107 153 self,
				)
			)

			(enterBooth
				(if (<= machineSpeed pcAT)
					(music
						number			sFanfare,
						loop				1,
						play,
					)
				)
				(ego
					view			vLroom,
					loop			1,
					cel			0,
					setCycle		EndLoop self,
				)
			)

			(shakeBooth
				(ego
					loop			2,
					cel			0,
					posn			(ego x) (- (ego y) 16),
					setCycle		EndLoop self,
				)
			)

			(shakeAgain
				(= cycles 22)
			)

			(nextLoop2
				(ego
					view			vLroom,
					loop			3,
					cel			0,
					setCycle		EndLoop self,
				)					 				
			)

			(andShakeAgain
				(Print "He's back!"
					HIGH
					#font			MASH_18  
					#time			3
					#dispose
				)
				(ShakeScreen 7 3)
				(delay 5)
			)

			(leaveBooth
				(= normalEgoView vLarry)
				(NormalEgo facingWest)
				(HandsOff)
				(ego
					posn				(ego x) (+ (ego y) 16),
					setPri			10,
					setMotion		MoveTo 85 153 self,
				)
			)

			(waitAsec
				(= cycles 22)
			)

			(lowerBooth
				(aBooth
					setMotion		MoveTo 111 211 self,
				)
				(= cycles 33)
			)

			(plot7
				(SetFlg beenIn220)
				(ego
					setMotion		MoveTo 107 153 self,
				)
				(Format @string "Look out, girls!
					Just when you thought it was safe to dive back into the
					gene pool, the original swinger is at it again!")	
				(= seconds (PrintPlot))
			)

			(plotDone
				(= cycles 33)
			)

			(flushBooth
				(aBooth stopUpd)
			)

			(readyToPlayGame
				(NormalEgo facingEast)
				(PutInRoom iWood 210)
 				(PutInRoom iCreditCard 216)
				(= egoState NORMAL)
				(music
					number		sVillageRegion,
					loop			bgMusicLoops,
					play,
				)
			)
		)
	)
)


(instance aCredit1 of Prop
	(properties
		view					vRoom
;		loop					lGraphicsBy
;		cel					0
		x						288
		y						131
		cycleSpeed			1
	)

	(method (init)
		(super init)
		(self
			setPri			15,
			ignoreActors,
		)
	)
)
(instance aCredit2 of Prop
	(properties
		view					vRoom
		loop					lWilliam
;		cel					0
		x						288
		y						154
		cycleSpeed			1
	)

	(method (init)
		(super init)
		(self
			setPri			15,
			ignoreActors,
			setScript		CreditsScript,
		)
	)
)

(instance CreditsScript of Script
	(method (changeState newState)
		(switch (= state newState)

			(doCredits
				(delay 4)
			)

			(doCredits1
				(aCredit1 setCycle EndLoop)
				(= cycles 16)
			)

			(doCredits2
				(aCredit2 setCycle EndLoop)
				(= cycles 22)
			)

			(doCredits3
				(aCredit2 setCycle BegLoop self)
			)

			(doCredits4
				(aCredit2
					loop			lRoger,
					setCycle		EndLoop
				)
				(= cycles 22)
			)

			(doCredits5
				(SetFlg sawCredits220)
				(aCredit1 setCycle BegLoop)
				(aCredit2 setCycle BegLoop self)
			)

			(clearCredits
				(aCredit1 dispose)
				(aCredit2 dispose)
			)
		)
	)
)
(procedure (PrintPlot &tmp t )
	(Print @string
		#at 			10 5 
		#width 		290
		#time			(= t (PrintDelay @string))
		#dispose
	)
	(return (+ 3 t))
)

(instance aBooth of Actor
	(properties
		view				vLroom
;		loop				lBooth
;		cel				cBooth
		x					111
		y					211
		illegalBits		0
	)

	(method (init)
		(super init)
		(self
			ignoreActors,
			setLoop			lBooth,
			setPri			11,
		)
	)
)
